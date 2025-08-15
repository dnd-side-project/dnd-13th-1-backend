package team1.allo.housework.repository.houseworkmember;

import java.util.List;

import team1.allo.housework.entity.HouseWorkMember;

public interface HouseWorkMemberCustomRepository {

	List<HouseWorkMember> findByHouseWorkIds(List<Long> houseWorkIds);
}
