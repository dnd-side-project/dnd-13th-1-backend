package team1.allo.housework.repository.housework;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import team1.allo.housework.entity.HouseWork;

public interface HouseWorkCustomRepository {

	List<LocalDate> findTaskDatesBetween(Long groupId, LocalDate from, LocalDate to);

	List<HouseWork> findHouseWorkRecent(Long groupId, Long receiverId, LocalDate currentDate);

	Long countCompletedHouseWorkByGroup(Long groupId, LocalDate currentDate);

	Long countCompletedHouseWorkByMember(Long memberId);

	Long countCompletedHouseWorkByMember(Long memberId, LocalDate currentDate);

	Long countCompletedHouseWorkByMember(Long memberId, LocalDate startDate, LocalDate endDate);

	Long countHouseWorkByMember(Long memberId, LocalDate currentDate);

	Long countHouseWorkByMember(Long memberId, LocalDate startDate, LocalDate endDate);

	Map<LocalDate, Long> getWeeklyCompletedHouseWorkCountByMember(
		Long memberId,
		LocalDate lastMonday,
		LocalDate lastSunday
	);
}
