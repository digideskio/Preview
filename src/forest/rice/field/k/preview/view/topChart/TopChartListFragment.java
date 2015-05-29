package forest.rice.field.k.preview.view.topChart;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import forest.rice.field.k.preview.R;
import forest.rice.field.k.preview.entity.Item;
import forest.rice.field.k.preview.manager.MediaPlayerManager;
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
//		getActivity()
//		.getFragmentManager().beginTransaction()
//        .replace(R.id.container, new CollectionFragment())
//        .addToBackStack(null)
//        .commit();
		try {
			MediaPlayerManager manager = MediaPlayerManager.getInstance();
			
			if(playingList.contains(position)) {
				manager.stop();
				playingList.clear();
			} else {
				
				List<String> previewString = new ArrayList<String>();
				for(int i = position; i < itemList.size(); i++) {
					previewString.add(itemList.get(i).previewUrl);
				}
				
//				Item item = itemList.get(position);
//				manager.setDataSourceAndPlay(getActivity(), item.previewUrl);
				
				manager.setDataSource(previewString);
				manager.playALl();
				
				playingList.clear();
				playingList.add(position);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void callback(List<Item> list) {
		itemList = list;
		TopChartArrayAdapter adapter = new TopChartArrayAdapter(getActivity(), list);
		setListAdapter(adapter);
	}
}
