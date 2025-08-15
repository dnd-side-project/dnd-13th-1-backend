package team1.allo.group.service.dto;

import java.time.LocalDate;
import java.util.List;

public record HouseWorkResponse(
	Long houseWorkId,
	String houseworkTitle,
	List<TagForHouseWorkListResponse> houseworkTag,
	LocalDate houseworkDate,
	List<MemberResponse> houseworkMembers
) {
}
