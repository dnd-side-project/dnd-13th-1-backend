package team1.housework.group.repository.houseworktag;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.housework.group.entity.HouseWorkTag;

public interface HouseWorkTagRepository extends JpaRepository<HouseWorkTag, Long>, HouseWorkTagCustomRepository {
}
