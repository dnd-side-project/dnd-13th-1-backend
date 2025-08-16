package team1.allo.housework.service.dto;

import team1.allo.housework.entity.HouseWork;

public record HouseWorkRecentResponse(
	String placeName,
	Long houseWorkId,
	String houseWorkName
) {

	public static HouseWorkRecentResponse from(HouseWork houseWork) {
		return new HouseWorkRecentResponse(
			houseWork.getPlace().getName(),
			houseWork.getId(),
			houseWork.getName()
		);
	}
}
