package team1.allo.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import team1.allo.auth.jwt.AppJwtProps;

@Configuration
@EnableConfigurationProperties(AppJwtProps.class)
public class JwtConfig { }

