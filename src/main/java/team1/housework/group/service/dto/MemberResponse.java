package team1.housework.group.service.dto;

public record MemberResponse(
	Long memberId,
	String memberNickName,
	String memberProfileImageUrl
) {
}
