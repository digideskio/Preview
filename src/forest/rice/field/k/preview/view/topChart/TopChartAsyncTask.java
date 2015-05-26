package forest.rice.field.k.preview.view.topChart;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import forest.rice.field.k.preview.entity.Item;
import forest.rice.field.k.preview.manager.ITunesRssManager;
import forest.rice.field.k.preview.request.ITunesRssRequest;

public class TopChartAsyncTask extends AsyncTask<String, String, List<Item>> {
	
	public TopChartAsyncTaskCallback callback;

	@Override
	protected List<Item> doInBackground(String... arg0) {
		
		ITunesRssRequest request = new ITunesRssRequest();
		List<Item> itemList = new ArrayList<Item>();
		try {
			
			ITunesRssManager manager = ITunesRssManager.getInstance();
			manager.parse(request.getJson());
			
			itemList = manager.getItemList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return itemList;
	}
	
	@Override
	protected void onPostExecute(List<Item> result) {
		super.onPostExecute(result);
		
		if(callback != null) {
			callback.callback(result);
		}
	}
	
	public interface TopChartAsyncTaskCallback {
		void callback(List<Item> list);
	}

}
