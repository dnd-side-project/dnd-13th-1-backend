package team1.housework.group.repository.houseworktag;

import java.util.List;

import team1.housework.group.entity.HouseWorkTag;

public interface HouseWorkTagCustomRepository {

	List<HouseWorkTag> findByHouseWorkIds(List<Long> houseWorkIds);
}
