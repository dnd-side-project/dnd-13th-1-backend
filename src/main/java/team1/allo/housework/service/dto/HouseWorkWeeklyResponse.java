package team1.allo.housework.service.dto;

import java.util.Map;

public record HouseWorkWeeklyResponse(
	int completed,
	int total,
	Map<String, Long> weeklyCompleted
) {
}
