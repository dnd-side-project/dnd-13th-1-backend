package team1.allo.housework.repository.houseworktag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.allo.housework.entity.HouseWorkTag;

public interface HouseWorkTagRepository extends JpaRepository<HouseWorkTag, Long>, HouseWorkTagCustomRepository {

	void deleteByHouseWorkId(Long houseWorkId);

	List<HouseWorkTag> findByHouseWorkId(Long houseWorkId);
}
