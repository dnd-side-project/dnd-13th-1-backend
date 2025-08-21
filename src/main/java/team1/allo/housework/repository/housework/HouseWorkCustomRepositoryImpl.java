package team1.allo.housework.repository.housework;

import static team1.allo.housework.entity.QHouseWork.*;
import static team1.allo.housework.entity.QHouseWorkMember.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team1.allo.housework.entity.HouseWork;

@RequiredArgsConstructor
public class HouseWorkCustomRepositoryImpl implements HouseWorkCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<LocalDate> findTaskDatesBetween(Long groupId, LocalDate from, LocalDate to) {
		return queryFactory.select(houseWork.taskDate)
			.from(houseWork)
			.where(
				houseWork.taskDate.between(from, to),
				houseWork.group.id.eq(groupId)
			)
			.fetch();
	}

	@Override
	public List<HouseWork> findHouseWorkRecent(Long groupId, Long receiverId, LocalDate currentDate) {
		LocalDate weekAgo = currentDate.minusDays(6);

		return queryFactory.selectFrom(houseWork)
			.join(houseWorkMember).on(houseWork.id.eq(houseWorkMember.houseWork.id))
			.join(houseWork.place).fetchJoin()
			.where(
				houseWork.group.id.eq(groupId),
				houseWorkMember.memberId.eq(receiverId),
				houseWork.completedDate.between(weekAgo, currentDate)
			)
			.orderBy(houseWork.completedDate.desc())
			.fetch();
	}

	@Override
	public Long countCompletedHouseWorkByGroup(Long groupId, LocalDate currentDate) {
		return queryFactory.select(houseWork.count())
			.from(houseWork)
			.where(
				houseWork.group.id.eq(groupId),
				houseWork.completedDate.eq(currentDate)
			)
			.fetchOne();
	}

	@Override
	public Long countCompletedHouseWorkByMember(Long memberId, LocalDate currentDate) {
		return queryFactory.select(houseWork.count())
			.from(houseWork)
			.leftJoin(houseWorkMember).on(houseWork.id.eq(houseWorkMember.houseWork.id))
			.where(
				houseWorkMember.memberId.eq(memberId),
				houseWork.completedDate.eq(currentDate)
			)
			.fetchOne();
	}

	@Override
	public Long countHouseWorkByMember(Long memberId, LocalDate currentDate) {
		return queryFactory.select(houseWork.count())
			.from(houseWork)
			.leftJoin(houseWorkMember).on(houseWork.id.eq(houseWorkMember.houseWork.id))
			.where(
				houseWorkMember.memberId.eq(memberId),
				houseWork.taskDate.eq(currentDate)
			)
			.fetchOne();
	}

	@Override
	public Long countHouseWorkByMember(Long memberId, LocalDate startDate, LocalDate endDate) {
		return queryFactory.select(houseWork.count())
			.from(houseWork)
			.leftJoin(houseWorkMember).on(houseWork.id.eq(houseWorkMember.houseWork.id))
			.where(
				houseWorkMember.memberId.eq(memberId),
				houseWork.taskDate.between(startDate, endDate)
			)
			.fetchOne();
	}

	@Override
	public Map<LocalDate, Long> getWeeklyCompletedHouseWorkCountByMember(
		Long memberId,
		LocalDate lastMonday,
		LocalDate lastSunday
	) {
		List<Tuple> results = queryFactory.select(houseWork.completedDate, houseWork.count())
			.from(houseWork)
			.leftJoin(houseWorkMember).on(houseWork.id.eq(houseWorkMember.houseWork.id))
			.where(
				houseWorkMember.memberId.eq(memberId),
				houseWork.completedDate.between(lastMonday, lastSunday)
			)
			.groupBy(houseWork.completedDate)
			.orderBy(houseWork.completedDate.asc())
			.fetch();

		Map<LocalDate, Long> weeklyCompleted = new LinkedHashMap<>();
		for (int i = 0; i < 7; i++) {
			weeklyCompleted.put(lastMonday.plusDays(i), 0L);
		}

		for (Tuple result : results) {
			weeklyCompleted.put(
				result.get(houseWork.completedDate),
				result.get(houseWork.count())
			);
		}

		return weeklyCompleted;
	}
}
