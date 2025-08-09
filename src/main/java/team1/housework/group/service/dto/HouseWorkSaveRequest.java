package team1.housework.group.service.dto;

import java.time.LocalDate;
import java.util.List;

public record HouseWorkSaveRequest(
	String houseWorkName,
	Long placeId,
	List<Long> tags,
	LocalDate startDate,
	LocalDate dueDate,
	String routinePolicy,
	List<String> dayOfWeek,
	boolean isNotified
) {
}
