package team1.allo.group.service.dto;

public record MyGroupResponse(
	Long groupId,
	String inviteCode,
	String backGroundType
) {
}
