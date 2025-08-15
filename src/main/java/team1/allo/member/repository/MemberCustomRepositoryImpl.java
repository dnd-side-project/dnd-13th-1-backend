package team1.allo.member.repository;

import static team1.allo.member.entity.QMember.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team1.allo.member.entity.Member;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Member> findByIds(List<Long> ids) {
		return queryFactory.selectFrom(member)
			.where(member.id.in(ids))
			.fetch();
	}
}
