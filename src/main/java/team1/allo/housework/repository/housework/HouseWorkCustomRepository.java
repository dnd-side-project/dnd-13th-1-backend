package team1.allo.housework.repository.housework;

import java.time.LocalDate;
import java.util.List;

import team1.allo.housework.entity.HouseWork;

public interface HouseWorkCustomRepository {

	List<LocalDate> findTaskDatesBetween(Long groupId, LocalDate from, LocalDate to);

	List<HouseWork> findHouseWorkRecent(Long groupId, Long receiverId, LocalDate currentDate);

	Long countCompletedHouseWorkByGroup(Long memberId, LocalDate currentDate);

	Long countCompletedHouseWorkByMember(Long memberId, LocalDate currentDate);
}
