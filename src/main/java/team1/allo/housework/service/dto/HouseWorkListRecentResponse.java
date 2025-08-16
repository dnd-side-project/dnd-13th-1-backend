package team1.allo.housework.service.dto;

import java.time.LocalDate;
import java.util.List;

public record HouseWorkListRecentResponse(
	LocalDate date,
	List<HouseWorkRecentResponse> houseWorks
) {
}
