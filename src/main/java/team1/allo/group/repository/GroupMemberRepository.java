package team1.allo.group.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.allo.group.entity.GroupMember;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

	List<GroupMember> findByGroupId(Long groupId);

	Optional<GroupMember> findFirstByMemberId(Long memberId);
}
