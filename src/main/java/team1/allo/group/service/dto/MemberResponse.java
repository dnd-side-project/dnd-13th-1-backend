package team1.allo.group.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public record MemberResponse(
	Long memberId,
	@JsonInclude(JsonInclude.Include.NON_NULL) String memberNickName,
	String memberProfileImageUrl
) {
}
