package forest.rice.field.k.preview.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class AbstractRequest {

	abstract public String getJson() throws IOException;
	
	protected String getStringFromInputStream(InputStream stream) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		StringBuilder builder = new StringBuilder();
		String line;
		while((line = br.readLine()) != null) {
			builder.append(line);
		}
		return builder.toString();
	}
}
