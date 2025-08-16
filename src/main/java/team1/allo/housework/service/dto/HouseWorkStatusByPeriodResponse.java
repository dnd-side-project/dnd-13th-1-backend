package team1.allo.housework.service.dto;

import java.time.LocalDate;

public record HouseWorkStatusByPeriodResponse(
	LocalDate date,
	boolean hasHousework
) {
}
