package team1.housework.group.service.dto;

import java.time.LocalDate;

public record HouseWorkStatusByPeriodResponse(
	LocalDate date,
	boolean hasHousework
) {
}
