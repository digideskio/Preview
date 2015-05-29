package forest.rice.field.k.preview.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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

	
}
