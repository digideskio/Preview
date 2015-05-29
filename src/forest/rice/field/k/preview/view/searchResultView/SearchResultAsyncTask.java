package forest.rice.field.k.preview.view.searchResultView;

import android.os.AsyncTask;
import forest.rice.field.k.preview.entity.SearchResultItems;
import forest.rice.field.k.preview.manager.ITunesApiManager;
import forest.rice.field.k.preview.request.ITunesApiSearchRequest;

public class SearchResultAsyncTask extends AsyncTask<String, String, SearchResultItems> {
	
	public SearchResultAsyncTaskCallback callback;

	@Override
	protected SearchResultItems doInBackground(String... params) {
		ITunesApiSearchRequest request = new ITunesApiSearchRequest(params[0]);
		ITunesApiManager manager = new ITunesApiManager();
		SearchResultItems result  = new SearchResultItems();
		try {
			result = manager.parseResultJson(request.getJson());
		} catch (Exception e) {
		}
		
		return result;
	}
	
	@Override
	protected void onPostExecute(SearchResultItems result) {
		super.onPostExecute(result);
		
		callback.callback(result);
	}
	
	public interface SearchResultAsyncTaskCallback {
		void callback(SearchResultItems result);
	}
}
