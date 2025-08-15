package team1.allo.housework.repository.houseworktag;

import java.util.List;

import team1.allo.housework.entity.HouseWorkTag;

public interface HouseWorkTagCustomRepository {

	List<HouseWorkTag> findByHouseWorkIds(List<Long> houseWorkIds);
}
