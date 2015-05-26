package forest.rice.field.k.preview.manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import forest.rice.field.k.preview.entity.Item;

public class ITunesRssManager {
	
	private static ITunesRssManager manager = null;
	
	private List<Item> itemList;
	
	private ITunesRssManager() {
	}
	
	public static ITunesRssManager getInstance() {
		if(manager == null) {
			manager = new ITunesRssManager();
		}
		
		return manager;
	}
	
	public void parse(String jsonString) throws Exception{
		JSONObject json = new JSONObject(jsonString);
		
		JSONArray array = json.getJSONObject("feed").getJSONArray("entry");
		itemList = new ArrayList<Item>();
		
		for (int i = 0; i < array.length(); i++) {
			
			Item item = new Item();
			
			JSONObject object = array.getJSONObject(i);
			
			item.name = object.getJSONObject("im:name").getString("label");
			item.artist = object.getJSONObject("im:artist").getString("label");
			JSONArray imageArray = object.getJSONArray("im:image");
			item.image = imageArray.getJSONObject(imageArray.length()-1).getString("label");
			item.collectionName = object.getJSONObject("im:collection").getJSONObject("link").getJSONObject("attributes").getString("href");
			item.previewUrl = object.getJSONArray("link").getJSONObject(1).getJSONObject("attributes").getString("href");
			
			itemList.add(item);
		}
	}
	
	public List<Item> getItemList() {
		return itemList;
	}
}
