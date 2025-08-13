package team1.housework.member.repository;

import java.util.List;

import team1.housework.member.entity.Member;

public interface MemberCustomRepository {

	List<Member> findByIds(List<Long> ids);
}
