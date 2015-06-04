package forest.rice.field.k.preview.view.searchResultView;

import android.os.AsyncTask;
import forest.rice.field.k.preview.entity.Tracks;
import forest.rice.field.k.preview.request.ITunesApiSearchRequest;

public class SearchResultAsyncTask extends AsyncTask<String, String, Tracks> {
	
	public SearchResultAsyncTaskCallback callback;

	@Override
	protected Tracks doInBackground(String... params) {
		ITunesApiSearchRequest request = new ITunesApiSearchRequest(params[0]);
		Tracks result  = new Tracks();
		try {
			result = request.parseResultJson(request.getJson());
		} catch (Exception e) {
		}
		
		return result;
	}
	
	@Override
	protected void onPostExecute(Tracks result) {
		super.onPostExecute(result);
		
		callback.callback(result);
	}
	
	public interface SearchResultAsyncTaskCallback {
		void callback(Tracks result);
	}
}
