package team1.housework.group.repository.houseworkmember;

import static team1.housework.group.entity.QHouseWorkMember.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team1.housework.group.entity.HouseWorkMember;

@RequiredArgsConstructor
public class HouseWorkMemberCustomRepositoryImpl implements HouseWorkMemberCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<HouseWorkMember> findByHouseWorkIds(List<Long> houseWorkIds) {
		return queryFactory.selectFrom(houseWorkMember)
			.where(houseWorkMember.houseWork.id.in(houseWorkIds))
			.fetch();
	}
}
