package team1.housework.storage.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import team1.housework.storage.StorageUtils;

@Service
@Profile("!prod")
public class LocalStorageService implements StorageService {

	@Value("${file.upload.directory:uploads}")
	private String uploadDirectory;

	@Override
	public String uploadImageAndGetUrl(MultipartFile image) {
		StorageUtils.verifyImage(image);

		// 절대 경로로 변환
		File directory = new File(uploadDirectory).getAbsoluteFile();
		if (!directory.exists()) {
			boolean created = directory.mkdirs();
			if (!created) {
				throw new RuntimeException("Failed to create directory: " + directory.getAbsolutePath());
			}
		}

		// 파일명 생성
		String fileName = StorageUtils.getFileName(image.getOriginalFilename());

		// 파일 저장
		try {
			File file = new File(directory, fileName);
			image.transferTo(file);

			return "https://fake/" + fileName;
		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
	}

	@Override
	public void deleteImage(String url) {
		String fileName = url.substring(url.lastIndexOf('/') + 1);
		File file = new File(uploadDirectory, fileName);
		if (file.exists()) {
			file.delete();
		}
	}
}
