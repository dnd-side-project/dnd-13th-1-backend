package team1.allo.housework.service.dto;

import java.time.LocalDate;

public record WeeklyHouseWorkCountDto(LocalDate completedDate, Long count) {
}
