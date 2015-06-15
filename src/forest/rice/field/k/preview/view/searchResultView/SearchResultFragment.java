
package forest.rice.field.k.preview.view.searchResultView;

import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import forest.rice.field.k.preview.view.searchResultView.SearchResultAsyncTask.SearchResultAsyncTaskCallback;

public class SearchResultFragment extends ListFragment implements
        SearchResultAsyncTaskCallback {

    public static final String KEYWORD = "keyword";

    private Tracks tracks = null;

    private String keyword = null;

    public SearchResultFragment() {
    }

    public static SearchResultFragment newInstance(String keyword) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putString(KEYWORD, keyword);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keyword = "";
        if (getArguments() != null) {
            keyword = getArguments().getString(KEYWORD);
        }

        SearchResultAsyncTask asyncTask = new SearchResultAsyncTask();
        asyncTask.callback = this;
        asyncTask.execute(keyword);
    }

    @Override
    public void onStart() {
        super.onStart();

        getActivity().getActionBar().setTitle(keyword);

        setEmptyText("No Result");
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
                    // 歌詞検索[アーティスト検索]
                    {
                        Intent i = IntentManager.createLyricsViewInBrowserIntent(
                                tracks.get(position).get(Track.artistName));
                        startActivity(i);
                    }
                        break;
                    case 5:
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

    @Override
    public void callback(Tracks result) {

        tracks = result;
        SearchResultArrayAdapter adapter = new SearchResultArrayAdapter(
                getActivity(), 0, result);
        setListAdapter(adapter);

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
