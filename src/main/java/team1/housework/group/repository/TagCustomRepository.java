package team1.housework.group.repository;

import java.util.List;

import team1.housework.group.entity.Tag;

public interface TagCustomRepository {

	List<Tag> findByIds(List<Long> ids);
}
