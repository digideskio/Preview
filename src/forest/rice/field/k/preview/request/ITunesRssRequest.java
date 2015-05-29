package forest.rice.field.k.preview.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ITunesRssRequest extends AbstractRequest {
	
	private String endpoint = "https://itunes.apple.com/jp/rss/topsongs/limit=100/json";
	
	@Override	
	public String getJson() throws IOException {
		URL url = new URL(endpoint);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();		
		return getStringFromInputStream(connection.getInputStream());
	}
	
	
}
