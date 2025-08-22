package team1.allo.housework.repository.housework;

import static team1.allo.housework.entity.QHouseWork.*;
import static team1.allo.housework.entity.QHouseWorkMember.*;

import java.time.LocalDate;
import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team1.allo.housework.entity.HouseWork;
import team1.allo.housework.service.dto.WeeklyHouseWorkCountDto;

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
	public Long countCompletedHouseWorkByMember(Long memberId) {
		return queryFactory.select(houseWork.count())
			.from(houseWork)
			.leftJoin(houseWorkMember).on(houseWork.id.eq(houseWorkMember.houseWork.id))
			.where(
				houseWorkMember.memberId.eq(memberId),
				houseWork.completed.isTrue()
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
	public Long countCompletedHouseWorkByMember(Long memberId, LocalDate startDate, LocalDate endDate) {
		return queryFactory.select(houseWork.count())
			.from(houseWork)
			.leftJoin(houseWorkMember).on(houseWork.id.eq(houseWorkMember.houseWork.id))
			.where(
				houseWorkMember.memberId.eq(memberId),
				houseWork.completedDate.between(startDate, endDate)
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
	public List<WeeklyHouseWorkCountDto> getWeeklyCompletedHouseWorkCountByMember(
		Long memberId,
		LocalDate lastMonday,
		LocalDate lastSunday
	) {
		return queryFactory.select(
				Projections.constructor(
					WeeklyHouseWorkCountDto.class,
					houseWork.completedDate,
					houseWork.count()
				)
			)
			.from(houseWork)
			.leftJoin(houseWorkMember).on(houseWork.id.eq(houseWorkMember.houseWork.id))
			.where(
				houseWorkMember.memberId.eq(memberId),
				houseWork.completedDate.between(lastMonday, lastSunday)
			)
			.groupBy(houseWork.completedDate)
			.orderBy(houseWork.completedDate.asc())
			.fetch();
	}
}
