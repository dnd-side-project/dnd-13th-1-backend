package team1.housework.storage;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class StorageUtils {

	public static void verifyImage(MultipartFile image) {
		if (image == null || image.isEmpty()) {
			throw new IllegalArgumentException("Image file is empty.");
		}
	}

	public static String verifyAndGetContentType(MultipartFile image) {
		String contentType = image.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			throw new IllegalArgumentException("Only image content types are allowed.");
		}
		return contentType;
	}

	public static String getFileName(String originalFilename) {
		if (originalFilename == null || originalFilename.isBlank()) {
			throw new IllegalArgumentException("File name is missing.");
		}

		String extension = "";
		int lastDotIndex = originalFilename.lastIndexOf('.');
		if (lastDotIndex != -1) {
			extension = originalFilename.substring(lastDotIndex);
		}
		return UUID.randomUUID() + extension;
	}
}
