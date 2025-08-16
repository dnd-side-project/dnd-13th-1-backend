package team1.allo.group.repository.tag;

import java.util.List;

import team1.allo.group.entity.Tag;

public interface TagCustomRepository {

	List<Tag> findByIds(List<Long> ids);
}
