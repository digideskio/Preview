package forest.rice.field.k.preview.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.entity.Tracks;

public class ITunesApiSearchRequest  extends AbstractRequest {
	
	private String keyword = null;
	
	public ITunesApiSearchRequest(String keyword) {
		this.keyword = keyword;
	}
	
	private final String endpoint = "https://itunes.apple.com/search?term=%s&country=jp&media=music&entity=song&lang=ja_jp";

	@Override
	public String getJson() throws IOException {
		URL url = new URL(String.format(endpoint, URLEncoder.encode(keyword, "UTF-8")));
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();		
		return getStringFromInputStream(connection.getInputStream());
	}
	
	public Tracks parseResultJson(String jsonString)
            throws Exception {

        JSONObject json = new JSONObject(jsonString);
        JSONArray results = json.getJSONArray("results");

        Tracks items = new Tracks();

        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);

            Track searchResult = new Track();

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
