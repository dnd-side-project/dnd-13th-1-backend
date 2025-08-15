package team1.allo.group.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.allo.group.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

	Optional<Group> findByInviteCode(String inviteCode);
}
