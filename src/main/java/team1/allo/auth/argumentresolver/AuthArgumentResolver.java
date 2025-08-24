package team1.allo.auth.argumentresolver;

import java.util.NoSuchElementException;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import team1.allo.auth.annotation.Auth;
import team1.allo.auth.jwt.JwtProvider;
import team1.allo.member.entity.Member;
import team1.allo.member.repository.MemberRepository;

@Slf4j
@Component
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
	public Mono<Object> resolveArgument(MethodParameter parameter,
		org.springframework.web.reactive.BindingContext bindingContext,
		ServerWebExchange exchange) {
		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return Mono.empty();
		}
		String token = authHeader.substring(7);
		Long memberId = jwtProvider.parseMemberId(token);
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new NoSuchElementException("Member not found"));
		log.info("Member found: {}", member.getId());
		return Mono.justOrEmpty(member);
	}
}

