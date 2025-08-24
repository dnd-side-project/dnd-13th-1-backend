package team1.allo.housework.service.dto;

import java.util.List;

public record HouseWorkListByPlaceResponse(
	List<HouseWorkByPlaceResponse> myHouseWork,
	List<HouseWorkByPlaceResponse> ourHouseWork
) {
}
