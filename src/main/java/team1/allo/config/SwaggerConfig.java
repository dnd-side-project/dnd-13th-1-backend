package team1.allo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openApi() {
		Info info = new Info()
			.title("Allo");

		SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer Token");

		SecurityScheme securityScheme = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.in(SecurityScheme.In.HEADER)
			.name("Authorization")
			.scheme("bearer")
			.bearerFormat("JWT");

		return new OpenAPI()
			.info(info)
			.addSecurityItem(securityRequirement)
			.components(
				new Components().addSecuritySchemes("Bearer Token", securityScheme)
			);
	}
}
