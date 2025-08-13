package team1.housework.storage.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import team1.housework.storage.StorageUtils;

@Service
@Profile("prod")
@RequiredArgsConstructor
public class S3StorageService implements StorageService {

	private final S3Template s3Template;

	@Value("${spring.cloud.aws.s3.bucket}")
	private String bucketName;

	@Override
	public String uploadImageAndGetUrl(MultipartFile image) {
		StorageUtils.verifyImage(image);
		String contentType = StorageUtils.verifyAndGetContentType(image);

		String key = StorageUtils.getFileName(image.getOriginalFilename());

		URL url;
		try (InputStream inputStream = image.getInputStream()) {
			url = s3Template.upload(
				bucketName,
				key,
				inputStream,
				ObjectMetadata.builder()
					.contentType(contentType)
					.build()
			).getURL();
		} catch (IOException e) {
			throw new RuntimeException("S3 Error", e);
		}
		System.out.println(url);
		return url.toString();
	}

	@Override
	public void deleteImage(String url) {
		String key;
		try {
			key = new URI(url)
				.toURL()
				.getPath()
				.substring(1);
		} catch (MalformedURLException | URISyntaxException e) {
			throw new RuntimeException("S3 Error", e);
		}
		s3Template.deleteObject(bucketName, key);
	}
}
