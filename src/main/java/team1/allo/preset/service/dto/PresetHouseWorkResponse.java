package team1.allo.preset.service.dto;

import java.util.List;

public record PresetHouseWorkResponse(
	Long houseWorkCatId,
	String catName,
	List<HouseWork> houseWorks
) {
	public record HouseWork(
		Long houseWorkId,
		String name
	) {
	}
}