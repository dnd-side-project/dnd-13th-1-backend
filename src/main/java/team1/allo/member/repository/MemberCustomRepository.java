package team1.allo.member.repository;

import java.util.List;

import team1.allo.member.entity.Member;

public interface MemberCustomRepository {

	List<Member> findByIds(List<Long> ids);
}
