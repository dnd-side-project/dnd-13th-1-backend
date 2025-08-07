package team1.housework.group.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.housework.group.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

	Optional<Group> findByInviteCode(String inviteCode);
}
