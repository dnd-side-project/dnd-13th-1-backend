package team1.allo.housework.service.dto;

import java.util.List;

import team1.allo.group.service.dto.MemberResponse;

public record HouseWorkByPlaceResponse(
	Long houseWorkId,
	String houseWorkTitle,
	List<MemberResponse> houseWorkMembers
) {
}
