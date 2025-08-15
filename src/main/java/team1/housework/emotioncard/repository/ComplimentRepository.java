package team1.housework.emotioncard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.housework.emotioncard.entity.Compliment;

public interface ComplimentRepository extends JpaRepository<Compliment, Long> {

	List<Compliment> findByEmotionCardId(Long emotionCardId);
}
