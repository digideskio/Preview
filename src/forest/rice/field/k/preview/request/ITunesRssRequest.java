package forest.rice.field.k.preview.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ITunesRssRequest {
	
	private String endpoint = "https://itunes.apple.com/jp/rss/topsongs/limit=100/json";
	
	public String getJson() throws IOException {
		URL url = new URL(endpoint);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();		
		return getStringFromInputStream(connection.getInputStream());
	}
	
	private String getStringFromInputStream(InputStream stream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		StringBuilder builder = new StringBuilder();
		String line;
		while((line = br.readLine()) != null) {
			builder.append(line);
		}
		return builder.toString();
	}
}
