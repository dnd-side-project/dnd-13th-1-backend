package team1.housework.group.service.dto;

import team1.housework.group.entity.HouseWork;

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
