package team1.housework.storage.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	String uploadImageAndGetUrl(MultipartFile image);

	void deleteImage(String url);
}
