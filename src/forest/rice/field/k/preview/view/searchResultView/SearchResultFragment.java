package forest.rice.field.k.preview.view.searchResultView;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import forest.rice.field.k.preview.entity.SearchResultItems;
import forest.rice.field.k.preview.manager.MediaPlayerManager;
import forest.rice.field.k.preview.view.searchResultView.SearchResultAsyncTask.SearchResultAsyncTaskCallback;

public class SearchResultFragment extends ListFragment implements
		SearchResultAsyncTaskCallback {

	public static final String KEYWORD = "keyword";
	
	SearchResultItems items = null;

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
		String keyword = "";
		if(getArguments() != null) {
			keyword = getArguments().getString(KEYWORD);
		}

		SearchResultAsyncTask asyncTask = new SearchResultAsyncTask();
		asyncTask.callback = this;
		asyncTask.execute(keyword);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		try {
			MediaPlayerManager manager = MediaPlayerManager.getInstance();
				List<String> previewString = new ArrayList<String>();
				for(int i = position; i < items.size(); i++) {
				previewString.add(items.get(i).get("previewUrl"));
				manager.setDataSource(previewString);
				manager.playALl();				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	@Override
	public void callback(SearchResultItems result) {
		items = result;
		SearchResultArrayAdapter adapter = new SearchResultArrayAdapter(
				getActivity(), 0, result);
		setListAdapter(adapter);
		
	}

}
