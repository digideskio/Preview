package forest.rice.field.k.preview.request.rss;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import forest.rice.field.k.preview.entity.Track;

public class Entry {

	private JSONObject entry;

	public Entry(JSONObject entry) {
		this.entry = entry;
	}

	public Track getTrack() {
		Track track = new Track();

		try {
			track.put(Track.trackName,
					entry.getJSONObject("im:name").getString("label"));
			track = this.setImage(track, entry.getJSONArray("im:image"));
			track = this.setCollection(track, entry.getJSONObject("im:collection"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return track;
	}

	private Track setImage(Track track, JSONArray object) {
		try {
			for (int i = 0; i < object.length(); i++) {
				JSONObject image = object.getJSONObject(i);

				String label = image.getJSONObject("attributes").getString(
						"label");

				switch (i) {
				case 0:
					track.put(Track.artworkUrl30, label);
					break;
				case 1:
					track.put(Track.artworkUrl60, label);
					break;
				case 2:
					track.put(Track.artworkUrl100, label);
					break;
				default:
					break;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return track;
	}

	private Track setCollection(Track track, JSONObject object) {
		try {
			track.put(Track.collectionName, object.getJSONObject("im:name")
					.getString("label"));

			track.put(Track.collectionViewUrl, object.getJSONObject("link")
					.getJSONObject("attributes").getString("href"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return track;
	}

	private Map<String, String> convertMap(JSONObject object) {
		Map<String, String> map = new HashMap<String, String>();

		Iterator<String> keys = object.keys();

		while (keys.hasNext()) {
			try {
				String key = keys.next();
				Object item = object.get(key);

				if (item instanceof String) {
					map.put(key, object.getString(key));
				} else {
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}

}
