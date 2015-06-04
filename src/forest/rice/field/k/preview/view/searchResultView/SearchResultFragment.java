package forest.rice.field.k.preview.view.searchResultView;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.mediaplayer.MediaPlayerNitificationService;
import forest.rice.field.k.preview.mediaplayer.MediaPlayerNitificationService.ServiceStatics;
import forest.rice.field.k.preview.view.searchResultView.SearchResultAsyncTask.SearchResultAsyncTaskCallback;

public class SearchResultFragment extends ListFragment implements
		SearchResultAsyncTaskCallback {

	public static final String KEYWORD = "keyword";
	
	private Tracks items = null;
	
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
		if(getArguments() != null) {
			keyword = getArguments().getString(KEYWORD);
		}

		SearchResultAsyncTask asyncTask = new SearchResultAsyncTask();
		asyncTask.callback = this;
		asyncTask.execute(keyword);
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		getActivity().getActionBar().setTitle(keyword);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent service = new Intent(getActivity(), MediaPlayerNitificationService.class);
		service.setAction(ServiceStatics.ACTION_TRACK_CLEAR);
		getActivity().startService(service);
		
		for(int i = position; i < items.size(); i++) {
			Intent service2 = new Intent(getActivity(), MediaPlayerNitificationService.class);
			service2.putExtra("TRACK", items.get(i));
			
			service2.setAction(ServiceStatics.ACTION_TRACK_ADD);
			getActivity().startService(service2);
		}
		
		service.setAction(ServiceStatics.ACTION_PLAY);
		getActivity().startService(service);
	}
	
	@Override
	public void callback(Tracks result) {
		items = result;
		SearchResultArrayAdapter adapter = new SearchResultArrayAdapter(
				getActivity(), 0, result);
		setListAdapter(adapter);		
	}
}
