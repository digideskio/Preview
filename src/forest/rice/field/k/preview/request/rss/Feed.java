package forest.rice.field.k.preview.request.rss;

import org.json.JSONArray;
import org.json.JSONObject;

public class Feed {

	private JSONObject feed;

	public Feed(JSONObject feed) {
		super();

		this.feed = feed;
	}

	public JSONArray getEntryArray() throws Exception {
		return feed.getJSONArray("entry");
	}

}
