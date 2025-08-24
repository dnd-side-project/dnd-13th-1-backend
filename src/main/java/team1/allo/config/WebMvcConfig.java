package team1.allo.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import team1.allo.auth.argumentresolver.AuthArgumentResolver;
import team1.allo.auth.jwt.JwtProvider;
import team1.allo.member.repository.MemberRepository;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepository;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new AuthArgumentResolver(jwtProvider, memberRepository));
	}
}
