
package forest.rice.field.k.preview.view.lyric;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;
import forest.rice.field.k.preview.R;
import forest.rice.field.k.preview.entity.Lyric;
import forest.rice.field.k.preview.entity.Lyrics;
import forest.rice.field.k.preview.request.LyricRequest;
import forest.rice.field.k.preview.view.dialog.LyricSelectDialogFragment;

public class LyricActivity extends Activity {

    public static final String EXTRA_ARTIST = "artist";
    public static final String EXTRA_TRACK = "track";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lyric);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private WebView webview;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_lyric, container, false);

            webview = (WebView) rootView.findViewById(R.id.lyric_webview);

            String artist = getActivity().getIntent().getStringExtra(EXTRA_ARTIST);
            String track = getActivity().getIntent().getStringExtra(EXTRA_TRACK);

            getActivity().getActionBar().setTitle(artist + " / " + track);

            new LyricsSearchTask().execute(artist, track);

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            // TODO Auto-generated method stub
            super.onAttach(activity);

        }

        class LyricsSearchTask extends AsyncTask<String, Integer, Lyrics> {

            @Override
            protected Lyrics doInBackground(String... params) {
                LyricRequest search = new LyricRequest();

                String artist = params[0];
                String track = params[1];

                Lyrics lyrics = search.getLyrics(artist, track);
                return lyrics;
            }

            @Override
            protected void onPostExecute(final Lyrics result) {
                switch (result.size()) {
                    case 0:
                        // なし
                        Toast.makeText(getActivity(), "見つかりませんでした。", Toast.LENGTH_SHORT);
                        break;
                    case 1:
                        // Webビューに表示
                        new LyricsGetTask().execute(result.get(0));
                        break;
                    default:
                    // ダイアログ表示
                    {
                        String[] items = new String[result.size()];
                        for (int i = 0; i < result.size(); i++) {
                            items[i] = result.get(i).get(Lyric.TRACK_NAME) + " / "
                                    + result.get(i).get(Lyric.ARTIST_NAME);
                        }

                        LyricSelectDialogFragment dialogFragment = LyricSelectDialogFragment
                                .newInstance(items);

                        dialogFragment.mOnClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new LyricsGetTask().execute(result.get(which));
                            }
                        };
                        dialogFragment.show(getFragmentManager(), "TAG");
                    }
                        break;
                }
            }
        }

        class LyricsGetTask extends AsyncTask<Lyric, Integer, Lyric> {
            @Override
            protected Lyric doInBackground(Lyric... params) {
                LyricRequest search = new LyricRequest();
                return search.getLyric(params[0]);
            }

            @Override
            protected void onPostExecute(Lyric result) {
                webview.loadDataWithBaseURL(null, result.get(Lyric.LYRICS), "text/html", "UTF-8",
                        null);
            }
        }
    }
}
