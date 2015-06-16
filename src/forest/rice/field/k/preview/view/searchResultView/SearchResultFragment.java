
package forest.rice.field.k.preview.view.searchResultView;

import android.os.Bundle;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.view.base.BaseListFragment;
import forest.rice.field.k.preview.view.searchResultView.SearchResultAsyncTask.SearchResultAsyncTaskCallback;

public class SearchResultFragment extends BaseListFragment implements
        SearchResultAsyncTaskCallback {

    public static final String KEYWORD = "keyword";

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

    }

    @Override
    public void callback(Tracks result) {
        this.tracks = result;
        SearchResultArrayAdapter adapter = new SearchResultArrayAdapter(
                getActivity(), result);
        setListAdapter(adapter);
    }
}
