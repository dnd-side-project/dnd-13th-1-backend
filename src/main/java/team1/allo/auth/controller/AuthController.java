package team1.allo.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import team1.allo.auth.annotation.Auth;
import team1.allo.auth.service.AuthService;
import team1.allo.auth.service.dto.KakaoLoginRequest;
import team1.allo.auth.service.dto.LoginResponse;
import team1.allo.auth.service.dto.MyProfileResponse;
import team1.allo.member.entity.Member;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@Operation(summary = "로그인")
	@PostMapping("/login")
	public LoginResponse login(@RequestBody KakaoLoginRequest request) {
		return authService.login(request.accessToken());
	}

	@Operation(summary = "로그인 사용자 정보 조회")
	@GetMapping("/me")
	public MyProfileResponse getMyProfile(@Parameter(hidden = true) @Auth Member member) {
		return authService.getMyProfile(member);
	}
}
