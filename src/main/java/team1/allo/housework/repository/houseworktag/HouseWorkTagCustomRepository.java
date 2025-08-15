package team1.allo.group.repository.houseworktag;

import java.util.List;

import team1.allo.group.entity.HouseWorkTag;

public interface HouseWorkTagCustomRepository {

	List<HouseWorkTag> findByHouseWorkIds(List<Long> houseWorkIds);
}
