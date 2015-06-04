package forest.rice.field.k.preview.view.topChart;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import forest.rice.field.k.preview.R;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.mediaplayer.MediaPlayerNitificationService;
import forest.rice.field.k.preview.mediaplayer.MediaPlayerNitificationService.ServiceStatics;
import forest.rice.field.k.preview.view.topChart.TopChartAsyncTask.TopChartAsyncTaskCallback;

public class TopChartListFragment extends ListFragment implements TopChartAsyncTaskCallback {

	private Tracks tracks;
	
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
		service.setAction(ServiceStatics.ACTION_TRACK_CLEAR);
		getActivity().startService(service);
		
		for (Track track : tracks) {
			Intent service2 = new Intent(getActivity(), MediaPlayerNitificationService.class);
			service2.putExtra("TRACK", track);
			
			service2.setAction(ServiceStatics.ACTION_TRACK_ADD);
			getActivity().startService(service2);	
		}
		
		service.setAction(ServiceStatics.ACTION_PLAY);
		getActivity().startService(service);
	}
	
	@Override
	public void callback(Tracks tracks) {
		this.tracks = tracks;
		TopChartArrayAdapter adapter = new TopChartArrayAdapter(getActivity(), tracks);
		setListAdapter(adapter);
	}
}
