package team1.allo.auth.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import team1.allo.auth.jwt.JwtProvider;
import team1.allo.member.entity.Member;
import team1.allo.member.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;
	private final MemberRepository memberRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			try {
				Long memberId = jwtProvider.parseMemberId(token);
				Member member = memberRepository.findById(memberId).orElse(null);

				if (member != null) {
					UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(member, null, null);
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} catch (Exception e) {
				// JWT가 만료되었거나 유효하지 않은 경우 → 그냥 인증 없이 통과 (401은 SecurityConfig에서 처리됨)
				SecurityContextHolder.clearContext();
			}
		}

		filterChain.doFilter(request, response);
	}
}
