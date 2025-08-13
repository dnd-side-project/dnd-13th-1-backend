package team1.housework.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.housework.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
}
