
package forest.rice.field.k.preview.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utils {

    public static String urlEncode(String input) {
        String encodedString = input;
        try {
            encodedString = URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return encodedString;
    }

}
