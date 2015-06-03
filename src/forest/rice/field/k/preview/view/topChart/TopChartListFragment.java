package forest.rice.field.k.preview.view.topChart;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import forest.rice.field.k.preview.R;
import forest.rice.field.k.preview.entity.Item;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.mediaplayer.MediaPlayerNitificationService;
import forest.rice.field.k.preview.mediaplayer.MediaPlayerNitificationService.Service;
import forest.rice.field.k.preview.view.topChart.TopChartAsyncTask.TopChartAsyncTaskCallback;

public class TopChartListFragment extends ListFragment implements TopChartAsyncTaskCallback {

	private List<Item> itemList;
	
	public static TopChartListFragment newInstance() {
		TopChartListFragment fragment = new TopChartListFragment();
		return fragment;
	}
	
	public TopChartListFragment() {
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TopChartAsyncTask task = new TopChartAsyncTask();
		task.callback = this;
		task.execute();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		getActivity().getActionBar().setTitle(getString(R.string.app_name));
	};
	
	List<Integer> playingList = new ArrayList<Integer>();
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		Intent service = new Intent(getActivity(), MediaPlayerNitificationService.class);
		service.setAction(Service.ACTION_TRACK_CLEAR);
		getActivity().startService(service);
		
		for(int i = position; i < itemList.size(); i++) {
			Item item = itemList.get(i);
			Track track = new Track();
			
			track.put(Track.trackName, item.name);
			track.put(Track.artworkUrl100, item.image);
			track.put(Track.collectionName, item.collectionName);
			track.put(Track.artistName, item.artist);
			track.put(Track.previewUrl, item.previewUrl);
			
			Intent service2 = new Intent(getActivity(), MediaPlayerNitificationService.class);
			service2.putExtra("TRACK", track);
			
			service2.setAction(Service.ACTION_TRACK_ADD);
			getActivity().startService(service2);
		}
		
		service.setAction(Service.ACTION_PLAY);
		getActivity().startService(service);
	}
	
	@Override
	public void callback(List<Item> list) {
		itemList = list;
		TopChartArrayAdapter adapter = new TopChartArrayAdapter(getActivity(), list);
		setListAdapter(adapter);
	}
}
