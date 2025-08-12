package team1.housework.group.repository.tag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.housework.group.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>, TagCustomRepository {

	List<Tag> findByGroupId(Long groupId);
}
