package team1.housework.s3.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {

	private final S3Template s3Template;

	@Value("${spring.cloud.aws.s3.bucket}")
	private String bucketName;

	public String uploadImageAndGetUrl(MultipartFile image) {
		String key = UUID.randomUUID() + "_" + image.getOriginalFilename();

		URL url;
		try (InputStream inputStream = image.getInputStream()) {
			url = s3Template.upload(
				bucketName,
				key,
				inputStream,
				ObjectMetadata.builder()
					.contentType(image.getContentType())
					.build()
			).getURL();
		} catch (IOException e) {
			throw new RuntimeException("S3 Error", e);
		}
		return url.toString();
	}

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
		String decodedKey = URLDecoder.decode(key, StandardCharsets.UTF_8);

		s3Template.deleteObject(bucketName, decodedKey);
	}
}
