package team1.housework.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.housework.group.entity.HouseWorkMember;

public interface HouseWorkMemberRepository extends JpaRepository<HouseWorkMember, Long> {
}
