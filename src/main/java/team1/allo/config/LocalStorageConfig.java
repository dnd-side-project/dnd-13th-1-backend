package team1.allo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import team1.allo.storage.service.LocalStorageService;
import team1.allo.storage.service.StorageService;

@Profile("!prod")
@Configuration
public class LocalStorageConfig {

	@Bean
	public StorageService localStorageService() {
		return new LocalStorageService();
	}
}
