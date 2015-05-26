package forest.rice.field.k.preview.manager;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;

public class MediaPlayerManager implements OnPreparedListener, OnCompletionListener {
	private static MediaPlayerManager manager;
	
	private MediaPlayer player;
	
	private MediaPlayerManager(){
		player = new MediaPlayer();
		player.setAudioStreamType(AudioManager.STREAM_MUSIC);
	}
	
	public static MediaPlayerManager getInstance() {
		if(manager == null) {
			manager = new MediaPlayerManager();
		}
		return manager;
	}
	
	public void setDataSourceAndPlay(Context context, String path) throws Exception {
		if(player.isPlaying()) {
			player.stop();
		}
		player.reset();
		player.setDataSource(path);
		player.prepareAsync();
		player.setOnPreparedListener(this);
		
	}

	@Override
	public void onPrepared(MediaPlayer arg0) {
		player.start();
	}
	
	public void stop() {
		if(player.isPlaying()) {
			player.stop();
		}
	}

	private List<String> pathList;
	private int index = 0;
	
	public void setDataSource(List<String> pathList) {
		this.pathList = pathList;
	}
	
	public void playALl(){
		index = 0;
		play(index);
	}
	
	private void play(int i) {
		try {
			player.reset();
			player.setDataSource(pathList.get(i));
			player.prepareAsync();
			player.setOnPreparedListener(this);
			player.setOnCompletionListener(this);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		index++;
		if(index >= pathList.size()) {
			return;
		}
		play(index);
		
	}
	
	
}
