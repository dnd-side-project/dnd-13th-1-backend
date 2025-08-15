package team1.allo.group.repository.tag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.allo.group.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>, TagCustomRepository {

	List<Tag> findByGroupId(Long groupId);
}
