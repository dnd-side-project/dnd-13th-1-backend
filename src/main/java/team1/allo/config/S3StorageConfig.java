package team1.allo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.awspring.cloud.s3.S3ObjectConverter;
import io.awspring.cloud.s3.S3OutputStreamProvider;
import io.awspring.cloud.s3.S3Template;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import team1.allo.storage.service.S3StorageService;
import team1.allo.storage.service.StorageService;

@Profile("prod")
@Configuration
public class S3StorageConfig {

	@Value("${spring.cloud.aws.credentials.access-key}")
	private String accessKey;

	@Value("${spring.cloud.aws.credentials.secret-key}")
	private String secretKey;

	@Value("${spring.cloud.aws.region.static}")
	private String region;

	@Bean
	public S3Client s3Client() {
		AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

		return S3Client.builder()
			.credentialsProvider(StaticCredentialsProvider.create(credentials))
			.region(Region.of(region))
			.build();
	}

	@Bean
	public S3Presigner s3Presigner() {
		AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

		return S3Presigner.builder()
			.credentialsProvider(StaticCredentialsProvider.create(credentials))
			.region(Region.of(region))
			.build();
	}

	@Bean
	public S3Template s3Template(
		S3Client s3Client,
		S3OutputStreamProvider s3OutputStreamProvider,
		S3ObjectConverter s3ObjectConverter,
		S3Presigner s3Presigner
	) {
		return new S3Template(s3Client, s3OutputStreamProvider, s3ObjectConverter, s3Presigner);
	}

	@Bean
	public StorageService s3StorageService(S3Template s3Template) {
		return new S3StorageService(s3Template);
	}
}
