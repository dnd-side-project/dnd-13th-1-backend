package team1.allo.auth.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import team1.allo.auth.service.dto.KakaoUserResponse;

@Component
@RequiredArgsConstructor
public class KakaoClient {

	private final WebClient webClient = WebClient.builder()
		.baseUrl("https://kapi.kakao.com")
		.build();

	public Mono<KakaoUserResponse> getUserInfo(String accessToken) {
		return webClient.get()
			.uri("/v2/user/me")
			.header("Authorization", "Bearer " + accessToken)
			.retrieve()
			.bodyToMono(KakaoUserResponse.class);
	}
}
