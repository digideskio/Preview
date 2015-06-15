
package forest.rice.field.k.preview.entity;

import java.util.HashMap;

public class Lyric extends HashMap<String, String> {

    public static final String TRACK_NAME = "TRACK_NAME";
    public static final String TRACK_URL = "TRACK_URL";
    public static final String ARTIST_NAME = "ARTIST_NAME";
    public static final String LYRICS = "LYRICS";

    private String baseUrl = "http://m.kget.jp";

    public String getLyricUrl() {
        if (get(TRACK_URL) == null) {
            return "";
        }
        return baseUrl + get(TRACK_URL).substring(1);
    }
}
