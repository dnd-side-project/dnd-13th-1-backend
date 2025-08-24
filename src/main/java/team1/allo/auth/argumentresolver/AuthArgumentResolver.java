package team1.allo.auth.argumentresolver;

import java.util.NoSuchElementException;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team1.allo.auth.annotation.Auth;
import team1.allo.auth.jwt.JwtProvider;
import team1.allo.member.entity.Member;
import team1.allo.member.repository.MemberRepository;

@Slf4j
@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepository;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Auth.class)
			&& parameter.getParameterType().equals(Member.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String authHeader = webRequest.getHeader(("Authorization"));
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new IllegalArgumentException("Authorization header is incorrect");
		}
		String token = authHeader.substring(7);
		Long memberId = jwtProvider.parseMemberId(token);
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new NoSuchElementException("Member not found"));
		log.info("Member found: {}", member.getId());
		return member;
	}

	// @Override
	// public Object resolveArgument(MethodParameter parameter,
	// 	org.springframework.web.reactive.BindingContext bindingContext,
	// 	ServerWebExchange exchange) {
	// 	String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
	// 	if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	// 		return Mono.empty();
	// 	}
	// 	String token = authHeader.substring(7);
	// 	Long memberId = jwtProvider.parseMemberId(token);
	// 	Member member = memberRepository.findById(memberId)
	// 		.orElseThrow(() -> new NoSuchElementException("Member not found"));
	// 	log.info("Member found: {}", member.getId());
	// 	return member;
	// }
}

