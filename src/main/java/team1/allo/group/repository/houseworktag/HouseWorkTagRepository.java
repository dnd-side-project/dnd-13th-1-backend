package team1.allo.group.repository.houseworktag;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.allo.group.entity.HouseWorkTag;

public interface HouseWorkTagRepository extends JpaRepository<HouseWorkTag, Long>, HouseWorkTagCustomRepository {
}
