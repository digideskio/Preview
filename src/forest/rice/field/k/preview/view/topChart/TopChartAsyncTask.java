package forest.rice.field.k.preview.view.topChart;

import android.os.AsyncTask;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.request.ITunesRssRequest;

public class TopChartAsyncTask extends AsyncTask<String, String, Tracks> {
	
	public TopChartAsyncTaskCallback callback;

	@Override
	protected Tracks doInBackground(String... arg0) {
	    Tracks tracks = new Tracks();
		try {
		    ITunesRssRequest request = new ITunesRssRequest();
			tracks = request.parse(request.getJson());
		} catch (Exception e) {
		}
		
		return tracks;
	}
	
	@Override
	protected void onPostExecute(Tracks tracks) {
		super.onPostExecute(tracks);
		
		if(callback != null) {
			callback.callback(tracks);
		}
	}
	
	public interface TopChartAsyncTaskCallback {
		void callback(Tracks tracks);
	}

}
