package team1.housework.group.repository.housework;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.housework.group.entity.HouseWork;

public interface HouseWorkRepository extends JpaRepository<HouseWork, Long>, HouseWorkCustomRepository {

	List<HouseWork> findByGroupIdAndTaskDate(Long groupId, LocalDate taskDate);
}
