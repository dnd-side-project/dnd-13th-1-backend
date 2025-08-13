package team1.housework.group.repository.houseworktag;

import static team1.housework.group.entity.QHouseWorkTag.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team1.housework.group.entity.HouseWorkTag;

@RequiredArgsConstructor
public class HouseWorkTagCustomRepositoryImpl implements HouseWorkTagCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<HouseWorkTag> findByHouseWorkIds(List<Long> houseWorkIds) {
		return queryFactory.selectFrom(houseWorkTag)
			.join(houseWorkTag.houseWork).fetchJoin()
			.join(houseWorkTag.tag).fetchJoin()
			.where(houseWorkTag.houseWork.id.in(houseWorkIds))
			.fetch();
	}
}
