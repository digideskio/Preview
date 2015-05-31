package forest.rice.field.k.preview.entity;

import java.util.HashMap;

public class Track extends HashMap<String, String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5145940658463698287L;

	public static String wrapperType = "wrapperType";
	public static String kind = "kind";
	public static String artistId = "artistId";
	public static String collectionId = "collectionId";
	public static String trackId = "trackId";
	public static String artistName = "artistName";
	public static String collectionName = "collectionName";
	public static String trackName = "trackName";
	public static String collectionCensoredName = "collectionCensoredName";
	public static String trackCensoredName = "trackCensoredName";
	public static String artistViewUrl = "artistViewUrl";
	public static String collectionViewUrl = "collectionViewUrl";
	public static String trackViewUrl = "trackViewUrl";
	public static String previewUrl = "previewUrl";
	public static String artworkUrl30 = "artworkUrl30";
	public static String artworkUrl60 = "artworkUrl60";
	public static String artworkUrl100 = "artworkUrl100";
	public static String collectionPrice = "collectionPrice";
	public static String trackPrice = "trackPrice";
	public static String releaseDate = "releaseDate";
	public static String collectionExplicitness = "collectionExplicitness";
	public static String trackExplicitness = "trackExplicitness";
	public static String discCount = "discCount";
	public static String discNumber = "discNumber";
	public static String trackCount = "trackCount";
	public static String trackNumber = "trackNumber";
	public static String trackTimeMillis = "trackTimeMillis";
	public static String country = "country";
	public static String currency = "currency";
	public static String primaryGenreName = "primaryGenreName";

	@Override
	public String get(Object key) {
		String value = super.get(key);
		return (value != null) ? value : "";
	}
}
