package team1.housework.group.repository.tag;

import static team1.housework.group.entity.QTag.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team1.housework.group.entity.Tag;

@RequiredArgsConstructor
public class TagCustomRepositoryImpl implements TagCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Tag> findByIds(List<Long> ids) {
		return queryFactory.selectFrom(tag)
			.where(tag.id.in(ids))
			.fetch();
	}
}
