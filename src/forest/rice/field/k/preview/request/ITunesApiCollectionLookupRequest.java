package forest.rice.field.k.preview.request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ITunesApiCollectionLookupRequest  extends AbstractRequest {
	
	private String id = null;
	
	public ITunesApiCollectionLookupRequest(String id) {
		this.id = id;
	}
	
	private final String endpoint = "https://itunes.apple.com/lookup?country=JP&entity=song&id=%s";

	@Override
	public String getJson() throws IOException {
		URL url = new URL(String.format(endpoint, id));
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();		
		return getStringFromInputStream(connection.getInputStream());
	}

	
}
