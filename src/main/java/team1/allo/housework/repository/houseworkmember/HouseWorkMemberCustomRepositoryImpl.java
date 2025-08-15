package team1.allo.housework.repository.houseworkmember;

import static team1.allo.group.entity.QHouseWorkMember.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team1.allo.housework.entity.HouseWorkMember;

@RequiredArgsConstructor
public class HouseWorkMemberCustomRepositoryImpl implements HouseWorkMemberCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<HouseWorkMember> findByHouseWorkIds(List<Long> houseWorkIds) {
		return queryFactory.selectFrom(houseWorkMember)
			.join(houseWorkMember.houseWork).fetchJoin()
			.where(houseWorkMember.houseWork.id.in(houseWorkIds))
			.fetch();
	}
}
