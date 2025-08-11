package team1.housework.preset.service.dto;

import java.util.List;

public record PresetHouseWorkResponse(
	Long houseWorkCatId,
	String catName,
	List<HouseWork> houseWorks
) {
	public record HouseWork(
		Long houseWorkId,
		String name
	) {}
}