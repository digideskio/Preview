package forest.rice.field.k.preview.entity;

import java.util.HashMap;

public class SearchResult extends HashMap<String, String>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5145940658463698287L;
	
	@Override
	public String get(Object key) {
		String value = super.get(key);
		return (value != null) ? value : "";
	}	
}
