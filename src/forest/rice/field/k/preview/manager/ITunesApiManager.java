package forest.rice.field.k.preview.manager;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import forest.rice.field.k.preview.entity.SearchResult;
import forest.rice.field.k.preview.entity.SearchResultItems;

public class ITunesApiManager {

	public SearchResultItems parseResultJson(String jsonString)
			throws Exception {

		JSONObject json = new JSONObject(jsonString);
		JSONArray results = json.getJSONArray("results");

		SearchResultItems items = new SearchResultItems();

		for (int i = 0; i < results.length(); i++) {
			JSONObject result = results.getJSONObject(i);

			SearchResult searchResult = new SearchResult();

			Iterator<String> keys = result.keys();

			while (keys.hasNext()) {
				String key = keys.next();
				searchResult.put(key, result.getString(key));
			}
			
			items.add(searchResult);
		}
		return items;
	}
}
