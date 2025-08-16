package team1.allo.group.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.allo.group.entity.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {

	List<Place> findByGroupId(Long groupId);
}
