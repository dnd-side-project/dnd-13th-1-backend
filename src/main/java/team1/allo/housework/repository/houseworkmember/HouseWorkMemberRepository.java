package team1.allo.housework.repository.houseworkmember;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.allo.housework.entity.HouseWorkMember;

public interface HouseWorkMemberRepository
	extends JpaRepository<HouseWorkMember, Long>, HouseWorkMemberCustomRepository {

	boolean existsByHouseWorkIdAndMemberId(Long houseWorkId, Long memberId);

	void deleteByHouseWorkId(Long houseWorkId);
}
