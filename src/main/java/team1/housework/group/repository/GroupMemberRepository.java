package team1.housework.group.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.housework.group.entity.GroupMember;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

	List<GroupMember> findByGroupId(Long groupId);
}
