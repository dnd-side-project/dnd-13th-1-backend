package team1.housework.group.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.housework.group.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

	List<Tag> findByGroupId(Long groupId);
}
