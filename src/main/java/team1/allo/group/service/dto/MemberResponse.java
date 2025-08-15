package team1.allo.group.service.dto;

public record MemberResponse(
	Long memberId,
	String memberNickName,
	String memberProfileImageUrl
) {
}
