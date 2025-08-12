package team1.housework.group.service.dto;

import java.time.LocalDate;

public record HouseWorkStatusByDateRangeResponse(
	LocalDate date,
	boolean hasHousework
) {
}
