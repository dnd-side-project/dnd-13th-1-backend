package team1.allo.auth.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoUserResponse(
	Long id,
	@JsonProperty("kakao_account")
	KakaoAccount kakaoAccount
) {
	@JsonIgnoreProperties(ignoreUnknown = true)
	public record KakaoAccount(Profile profile) {
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Profile(
		String nickname
	) {

	}
}
