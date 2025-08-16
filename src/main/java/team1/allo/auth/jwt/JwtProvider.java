package team1.allo.auth.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private final SecretKey secretKey;
	private final long expirationMillis;

	public JwtProvider(AppJwtProps props) {
		this.secretKey = Keys.hmacShaKeyFor(props.secret().getBytes());
		this.expirationMillis = props.expirationMillis();
	}

	public String generateToken(Long memberId) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expirationMillis);

		return Jwts.builder()
			.subject(String.valueOf(memberId))
			.issuedAt(now)
			.expiration(expiry)
			.signWith(secretKey)
			.compact();
	}

	public Long parseMemberId(String token) {
		return Long.valueOf(
			Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject()
		);
	}
}

