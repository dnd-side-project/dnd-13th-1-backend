package team1.allo.housework.service.dto;

import java.util.List;

public record HouseWorkListByDateResponse(
	List<HouseWorkResponse> myHouseWorkLeft,
	List<HouseWorkResponse> ourHouseWorkLeft,
	List<HouseWorkResponse> myHouseWorkCompleted,
	List<HouseWorkResponse> ourHouseWorkCompleted
) {
}
