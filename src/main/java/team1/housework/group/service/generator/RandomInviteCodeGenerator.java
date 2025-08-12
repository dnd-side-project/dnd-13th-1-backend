package team1.housework.group.service.generator;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class RandomInviteCodeGenerator implements InviteCodeGenerator {

	private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789"; // 0 제외
	private static final int CODE_LENGTH = 6;
	private static final SecureRandom RANDOM = new SecureRandom();

	public String generateInviteCode() {
		StringBuilder code = new StringBuilder(CODE_LENGTH);
		for (int i = 0; i < CODE_LENGTH; i++) {
			int index = RANDOM.nextInt(CHAR_POOL.length());
			code.append(CHAR_POOL.charAt(index));
		}
		return code.toString();
	}
}
