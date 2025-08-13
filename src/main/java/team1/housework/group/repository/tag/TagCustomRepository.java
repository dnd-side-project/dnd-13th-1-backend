package team1.housework.group.repository.tag;

import java.util.List;

import team1.housework.group.entity.Tag;

public interface TagCustomRepository {

	List<Tag> findByIds(List<Long> ids);
}
