package team1.housework.group.repository;

import static team1.housework.group.entity.QHouseWork.*;

import java.time.LocalDate;
import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HouseWorkCustomRepositoryImpl implements HouseWorkCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<LocalDate> findTaskDatesBetween(LocalDate from, LocalDate to, Long groupId) {
		return queryFactory.select(houseWork.taskDate)
			.from(houseWork)
			.where(
				houseWork.taskDate.between(from, to),
				houseWork.group.id.eq(groupId)
			)
			.fetch();
	}
}
