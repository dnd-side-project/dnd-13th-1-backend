package team1.housework.group.service.dto;

import java.util.List;

public record HouseWorkListByDateResponse(
	List<HouseWorkResponse> myHouseWorkLeft,
	List<HouseWorkResponse> ourHouseWorkLeft,
	List<HouseWorkResponse> myHouseWorkCompleted,
	List<HouseWorkResponse> ourHouseWorkCompleted
) {
}
