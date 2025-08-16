package team1.allo.auth.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team1.allo.auth.client.KakaoClient;
import team1.allo.auth.jwt.JwtProvider;
import team1.allo.auth.service.dto.KakaoUserResponse;
import team1.allo.auth.service.dto.LoginResponse;
import team1.allo.member.entity.Member;
import team1.allo.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final KakaoClient kakaoClient;
	private final MemberRepository memberRepository;
	private final JwtProvider jwtProvider;

	public LoginResponse login(String kakaoAccessToken) {
		// WebClient 호출을 동기(blocking)으로 받음
		KakaoUserResponse userInfo = kakaoClient.getUserInfo(kakaoAccessToken).block();

		if (userInfo == null) {
			throw new RuntimeException("카카오 사용자 정보를 불러오지 못했습니다.");
		}

		Long kakaoId = userInfo.id();
		String nickname = userInfo.kakaoAccount().profile().nickname();
		String code = "KAKAO_" + kakaoId;

		Member member = memberRepository.findByCode(code)
			.map(m -> m.toBuilder()
				.name(nickname)
				.build())
			.orElse(Member.builder()
				.code(code)
				.name(nickname)
				.build());

		member = memberRepository.save(member);

		String jwt = jwtProvider.generateToken(member.getId());
		return new LoginResponse("Bearer " + jwt);
	}
}

