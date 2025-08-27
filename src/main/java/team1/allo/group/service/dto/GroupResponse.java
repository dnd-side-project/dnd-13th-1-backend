package team1.allo.group.service.dto;

public record GroupResponse(
	Long groupId,
	String inviteCode,
	String backGroundType
) {
}
