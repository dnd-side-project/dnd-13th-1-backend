package team1.allo.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.allo.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
}
