package team1.allo.group.repository.housework;

import static team1.allo.group.entity.QHouseWork.*;
import static team1.allo.group.entity.QHouseWorkMember.*;

import java.time.LocalDate;
import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team1.allo.group.entity.HouseWork;

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
}
