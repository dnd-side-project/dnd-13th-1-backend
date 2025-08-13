package team1.housework.storage;

import java.util.UUID;

public class StorageUtils {

	public static String getFileName(String originalFilename) {
		String extension = "";
		int lastDotIndex = originalFilename.lastIndexOf('.');
		if (lastDotIndex != -1) {
			extension = originalFilename.substring(lastDotIndex);
		}
		return UUID.randomUUID() + extension;
	}
}
