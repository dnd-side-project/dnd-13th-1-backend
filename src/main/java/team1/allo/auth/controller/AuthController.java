package team1.allo.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team1.allo.auth.service.AuthService;
import team1.allo.auth.service.dto.KakaoLoginRequest;
import team1.allo.auth.service.dto.LoginResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody KakaoLoginRequest request) {
		return authService.login(request.accessToken());
	}

}
