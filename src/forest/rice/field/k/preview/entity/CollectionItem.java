package forest.rice.field.k.preview.entity;

import java.util.Map;

import android.util.SparseArray;

public class CollectionItem {

	public String resultCount;
	public Map<String, String> collection;
	public SparseArray<SparseArray<Map<String, String>>> disks;

	public void addTrack(Map<String, String> track) {
		String discNumber = track.get("discNumber");
		String trackNumber = track.get("trackNumber");

		SparseArray<Map<String, String>> disk = getDisk(discNumber);
		disk.put(Integer.parseInt(trackNumber), track);
	}

	private SparseArray<Map<String, String>> getDisk(String diskNumber) {
		if (disks == null) {
			disks = new SparseArray<SparseArray<Map<String, String>>>();
		}

		int number = Integer.parseInt(diskNumber);

		SparseArray<Map<String, String>> disk = disks.get(number);
		if (disk == null) {
			disk = new SparseArray<Map<String, String>>();

			disks.put(number, disk);
		}
		return disk;
	}

}
