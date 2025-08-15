package team1.allo.group.repository.houseworkmember;

import java.util.List;

import team1.allo.group.entity.HouseWorkMember;

public interface HouseWorkMemberCustomRepository {

	List<HouseWorkMember> findByHouseWorkIds(List<Long> houseWorkIds);
}
