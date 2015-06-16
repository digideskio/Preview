
package forest.rice.field.k.preview.view.base;

import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import forest.rice.field.k.preview.R;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.manager.IntentManager;
import forest.rice.field.k.preview.mediaplayer.MediaPlayerNitificationService;
import forest.rice.field.k.preview.mediaplayer.MediaPlayerNitificationService.ServiceStatics;
import forest.rice.field.k.preview.view.dialog.TrackSelectDialogFragment;
import forest.rice.field.k.preview.view.lyric.LyricActivity;

public abstract class BaseListFragment extends ListFragment {

    protected Tracks tracks;

    public BaseListFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();

        setEmptyText(getString(R.string.track_search_no_result));
    }

    @Override
    public void onListItemClick(ListView l, View v, final int position, long id) {

        TrackSelectDialogFragment dialogFragment = TrackSelectDialogFragment
                .newInstance(R.array.track_select_actions);
        dialogFragment.mOnClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        // 単一プレビュー
                        play(tracks.get(position));
                        break;
                    case 1:
                        // 連続プレビュー
                        playAll(tracks, position);
                        break;
                    case 2:
                    // iTunes
                    {
                        Intent i = IntentManager.createViewInBrowserIntent(tracks.get(position)
                                .get(Track.trackViewUrl));
                        startActivity(i);
                    }
                        break;
                    case 3:
                    // 歌詞検索
                    {
                        Intent i = IntentManager.createLyricsViewInBrowserIntent(
                                tracks.get(position).get(Track.artistName), tracks.get(position)
                                        .get(Track.trackName));
                        startActivity(i);
                    }
                        break;
                    case 4:
                    // 歌詞検索β
                    {
                        Intent i = new Intent(getActivity(), LyricActivity.class);
                        i.putExtra(LyricActivity.EXTRA_ARTIST,
                                tracks.get(position).get(Track.artistName));
                        i.putExtra(LyricActivity.EXTRA_TRACK,
                                tracks.get(position).get(Track.trackName));
                        startActivity(i);
                    }
                    default:
                        break;
                }
            }
        };

        dialogFragment.show(getFragmentManager(), "TAG");

    }

    private void play(Track track) {
        Intent service = new Intent(getActivity(), MediaPlayerNitificationService.class);
        service.setAction(ServiceStatics.ACTION_TRACK_CLEAR);
        getActivity().startService(service);

        Intent service2 = new Intent(getActivity(), MediaPlayerNitificationService.class);
        service2.putExtra("TRACK", track);

        service2.setAction(ServiceStatics.ACTION_TRACK_ADD);
        getActivity().startService(service2);

        service.setAction(ServiceStatics.ACTION_PLAY);
        getActivity().startService(service);
    }

    private void playAll(Tracks tracks, int startPosition) {
        Intent service = new Intent(getActivity(), MediaPlayerNitificationService.class);
        service.setAction(ServiceStatics.ACTION_TRACK_CLEAR);
        getActivity().startService(service);

        for (int i = startPosition; i < tracks.size(); i++) {
            Track track = tracks.get(i);

            Intent service2 = new Intent(getActivity(), MediaPlayerNitificationService.class);
            service2.putExtra("TRACK", track);

            service2.setAction(ServiceStatics.ACTION_TRACK_ADD);
            getActivity().startService(service2);
        }

        service.setAction(ServiceStatics.ACTION_PLAY);
        getActivity().startService(service);
    }

}
