package team1.housework.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.housework.group.entity.HouseWork;

public interface HouseWorkRepository extends JpaRepository<HouseWork, Long> {
}
