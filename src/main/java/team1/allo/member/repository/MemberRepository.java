package team1.allo.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.allo.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByCode(String code);

}
