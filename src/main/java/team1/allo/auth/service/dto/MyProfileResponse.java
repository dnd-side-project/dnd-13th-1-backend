package team1.allo.auth.service.dto;

import team1.allo.member.entity.Member;

public record MyProfileResponse(
	Long memberId,
	String memberNickName,
	String memberProfileImageUrl
) {

	public static MyProfileResponse from(Member member) {
		return new MyProfileResponse(
			member.getId(),
			member.getName(),
			member.getProfileImageUrl()
		);
	}
}
