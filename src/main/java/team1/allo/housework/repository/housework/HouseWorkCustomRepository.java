package team1.allo.housework.repository.housework;

import java.time.LocalDate;
import java.util.List;

import team1.allo.housework.entity.HouseWork;
import team1.allo.housework.service.dto.WeeklyHouseWorkCountDto;

public interface HouseWorkCustomRepository {

	List<LocalDate> findTaskDatesBetween(Long groupId, LocalDate from, LocalDate to);

	List<HouseWork> findHouseWorkRecent(Long groupId, Long receiverId, LocalDate currentDate);

	Long countCompletedHouseWorkByGroup(Long groupId, LocalDate currentDate);

	Long countCompletedHouseWorkByMember(Long memberId);

	Long countCompletedHouseWorkByMember(Long memberId, LocalDate currentDate);

	Long countCompletedHouseWorkByMember(Long memberId, LocalDate startDate, LocalDate endDate);

	Long countHouseWorkByMember(Long memberId, LocalDate currentDate);

	Long countHouseWorkByMember(Long memberId, LocalDate startDate, LocalDate endDate);

	List<WeeklyHouseWorkCountDto> getWeeklyCompletedHouseWorkCountByMember(
		Long memberId,
		LocalDate lastMonday,
		LocalDate lastSunday
	);
}
