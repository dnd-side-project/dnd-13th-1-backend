package team1.allo.group.service.dto;

import java.time.LocalDate;

public record HouseWorkStatusByPeriodResponse(
	LocalDate date,
	boolean hasHousework
) {
}
