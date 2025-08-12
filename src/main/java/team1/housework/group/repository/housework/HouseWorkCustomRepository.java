package team1.housework.group.repository.housework;

import java.time.LocalDate;
import java.util.List;

public interface HouseWorkCustomRepository {

	List<LocalDate> findTaskDatesBetween(Long groupId, LocalDate from, LocalDate to);
}
