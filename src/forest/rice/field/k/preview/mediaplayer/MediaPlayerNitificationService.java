
package forest.rice.field.k.preview.mediaplayer;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.android.uamp.AlbumArtCache;

import forest.rice.field.k.preview.R;
import forest.rice.field.k.preview.entity.Track;

public class MediaPlayerNitificationService extends Service implements
        OnPreparedListener, OnCompletionListener {

    private MediaPlayer player = null;
    private NotificationCompat.Builder notificationBuilder = null;
    private Queue<Track> playQueue = null;
    private Track playingTrack = null;
    private NotificationManagerCompat manager = null;

    public static Intent startIntent;

    public class ServiceStatics {
        public static final String ACTION_INIT = "INIT";
        public static final String ACTION_TRACK_ADD = "ADD";
        public static final String ACTION_TRACK_CLEAR = "CLEAR";
        public static final String ACTION_PLAY = "PLAY";
        public static final String ACTION_RESUME = "RESUME";
        public static final String ACTION_PAUSE = "PAUSE";
        public static final String ACTION_CLOSE = "CLOSE";
        public static final String ACTION_OPENWEB = "OPENWEB";
    }

    public class NotificationStatics {
        public static final int REQUEST_CODE_PLAY = 1001;
        public static final int REQUEST_CODE_PAUSE = 2001;
        public static final int REQUEST_CODE_RESUME = 3001;
        public static final int REQUEST_CODE_CLOSE = 4001;
        public static final int REQUEST_CODE_OPENWEB = 5001;
        public static final int NOTIFY_ID = 3001;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (intent == null) {
            return START_STICKY;
        }

        startIntent = intent;

        String action = intent.getAction();

        if (action == null) {
        } else if (action.equals(ServiceStatics.ACTION_TRACK_ADD)) {
            Serializable s = intent.getSerializableExtra("TRACK");
            add(new Track(s));
        } else if (action.equals(ServiceStatics.ACTION_TRACK_CLEAR)) {
            clear();
        } else if (action.equals(ServiceStatics.ACTION_PLAY)) {
            play();
        } else if (action.equals(ServiceStatics.ACTION_PAUSE)) {
            pause();
        } else if (action.equals(ServiceStatics.ACTION_RESUME)) {
            resume();
        } else if (action.equals(ServiceStatics.ACTION_CLOSE)) {
            close();
        } else if (action.equals(ServiceStatics.ACTION_OPENWEB)) {
            Uri uri = Uri.parse(playingTrack.get(Track.trackViewUrl));
            if (uri != null && uri.getHost() != null) {
                Intent openweb = new Intent(Intent.ACTION_VIEW, uri);
                openweb.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // openweb.setFlags(geta);
                getApplication().startActivity(openweb);

            }
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
        player = null;

        manager.cancelAll();
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    private void init() {
        if (player == null) {
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        if (notificationBuilder == null) {
            notificationBuilder = new NotificationCompat.Builder(
                    getApplicationContext());

            Intent playIntent = new Intent(getApplicationContext(),
                    MediaPlayerNitificationService.class);
            playIntent.setAction(ServiceStatics.ACTION_RESUME);
            PendingIntent playPIntent = PendingIntent.getService(
                    getApplicationContext(),
                    NotificationStatics.REQUEST_CODE_PLAY, playIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Intent pauseIntent = new Intent(getApplicationContext(),
                    MediaPlayerNitificationService.class);
            pauseIntent.setAction(ServiceStatics.ACTION_PAUSE);
            PendingIntent pausePIntent = PendingIntent.getService(
                    getApplicationContext(),
                    NotificationStatics.REQUEST_CODE_PAUSE, pauseIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Intent closeIntent = new Intent(getApplicationContext(),
                    MediaPlayerNitificationService.class);
            closeIntent.setAction(ServiceStatics.ACTION_CLOSE);
            PendingIntent closePIntent = PendingIntent.getService(
                    getApplicationContext(),
                    NotificationStatics.REQUEST_CODE_CLOSE, closeIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Intent openwebIntent = new Intent(getApplicationContext(),
                    MediaPlayerNitificationService.class);
            openwebIntent.setAction(ServiceStatics.ACTION_OPENWEB);
            PendingIntent openWebPIntent = PendingIntent.getService(getApplicationContext(),
                    NotificationStatics.REQUEST_CODE_OPENWEB, openwebIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            notificationBuilder.addAction(android.R.drawable.ic_media_play,
                    "Play", playPIntent);
            notificationBuilder.addAction(android.R.drawable.ic_media_pause,
                    "Pause", pausePIntent);
            notificationBuilder.setDeleteIntent(closePIntent);
            notificationBuilder.setContentIntent(openWebPIntent);

            notificationBuilder.setAutoCancel(false);
        }

        if (playQueue == null) {
            playQueue = new LinkedList<Track>();
        }

        manager = NotificationManagerCompat.from(getApplicationContext());
    }

    private void add(Track track) {
        if (playQueue != null) {
            playQueue.add(track);
        }
    }

    private void clear() {
        playQueue.clear();
    }

    private void play() {
        playingTrack = playQueue.poll();
        if (playingTrack == null) {
            stopSelf();
            return;
        }
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            try {
                String url = playingTrack.get(Track.previewUrl);
                player.reset();
                player.setDataSource(url);
                player.prepareAsync();
                player.setOnPreparedListener(this);
                player.setOnCompletionListener(this);
            } catch (Exception e) {
            }
        }

        if (notificationBuilder != null) {
            notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
            notificationBuilder.setContentTitle(playingTrack
                    .get(Track.trackName)); // 1行目
            notificationBuilder.setContentText(playingTrack
                    .get(Track.artistName)); // 2行目
            notificationBuilder.setSubText(playingTrack
                    .get(Track.collectionName)); // 3行目
            notificationBuilder.setContentInfo("Preview"); // 右端

            notificationBuilder.setOngoing(true);

            manager.notify(NotificationStatics.NOTIFY_ID,
                    notificationBuilder.build());

            // Bitmap画像は非同期で取得
            fetchBitmapFromURLAsync(playingTrack.getLargestArtwork());
        }
    }

    private void pause() {
        try {
            player.pause();
        } catch (IllegalStateException e) {
        }

        if (notificationBuilder != null) {
            notificationBuilder.setOngoing(false);
            manager.notify(NotificationStatics.NOTIFY_ID,
                    notificationBuilder.build());
        }
    }

    private void resume() {
        if (player != null) {
            player.start();
        }

        if (notificationBuilder != null) {
            notificationBuilder.setOngoing(true);

            manager.notify(NotificationStatics.NOTIFY_ID,
                    notificationBuilder.build());
        }
    }

    private void close() {
        stopSelf();
    }

    private void fetchBitmapFromURLAsync(final String bitmapUrl) {
        AlbumArtCache.getInstance().fetch(bitmapUrl,
                new AlbumArtCache.FetchListener() {
                    @Override
                    public void onFetched(String artUrl, Bitmap bitmap,
                            Bitmap icon) {
                        notificationBuilder.setLargeIcon(bitmap);
                        manager.notify(NotificationStatics.NOTIFY_ID,
                                notificationBuilder.build());
                    }
                });
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playingTrack = null;
        play();
    }

}
