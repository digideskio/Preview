
package forest.rice.field.k.preview.jsoup;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import forest.rice.field.k.preview.entity.Lyric;
import forest.rice.field.k.preview.entity.Lyrics;
import forest.rice.field.k.preview.request.AbstractRequest;
import forest.rice.field.k.preview.util.Utils;

public class Search extends AbstractRequest {

    @Override
    public String getJson() throws IOException {
        return null;
    }

    public Lyric getLyric(Lyric lyric) {

        try {
            URL url = new URL(lyric.getLyricUrl());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String html = getStringFromInputStream(connection.getInputStream());

            Document document = Jsoup.parse(html);
            if (document != null) {
                lyric.put(Lyric.LYRICS, document.getElementsByClass("lyric").get(0).html());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lyric;
    }

    public Lyrics getLyrics(String artist, String track) {
        Lyrics lyrics = new Lyrics();
        try {
            String baseUrl = "http://m.kget.jp/result.php?cat=0&artist=%s&title=%s&tieup=&phrase=";

            URL url = new URL(String.format(baseUrl, Utils.urlEncode(artist),
                    Utils.urlEncode(track)));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            String html = getStringFromInputStream(connection.getInputStream());

            Document document = Jsoup.parse(html);

            if (document == null) {
                return lyrics;
            }

            Element elements = document.getElementById("songlist");
            if (elements == null) {
                return lyrics;
            }

            Elements liElements = elements.getElementsByTag("li");
            for (Element li : liElements) {
                try {
                    Lyric lyric = new Lyric();

                    Element song = li.getElementsByClass("song").get(0);
                    String titleUrl = song.attr("href");
                    String titleVal = song.getElementsByTag("h2").get(0).text();
                    Element query = li.getElementsByClass("query").get(0);
                    String artistVal = query.text();

                    lyric.put(Lyric.TRACK_URL, titleUrl);
                    lyric.put(Lyric.TRACK_NAME, titleVal);
                    lyric.put(Lyric.ARTIST_NAME, artistVal);

                    lyrics.add(lyric);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lyrics;

    }
}
