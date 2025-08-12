package team1.housework.group.repository;

import java.util.List;

import team1.housework.group.entity.HouseWorkMember;

public interface HouseWorkMemberCustomRepository {

	List<HouseWorkMember> findByHouseWorkIds(List<Long> houseWorkIds);
}
