package team1.allo.group.repository.tag;

import static team1.allo.group.entity.QTag.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team1.allo.group.entity.Tag;

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
