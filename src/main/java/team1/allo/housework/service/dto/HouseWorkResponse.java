package team1.allo.housework.service.dto;

import java.time.LocalDate;
import java.util.List;

import team1.allo.group.service.dto.MemberResponse;

public record HouseWorkResponse(
	Long houseWorkId,
	String houseWorkTitle,
	String placeName,
	List<TagForHouseWorkListResponse> houseWorkTag,
	LocalDate houseWorkDate,
	List<MemberResponse> houseWorkMembers
) {
}
