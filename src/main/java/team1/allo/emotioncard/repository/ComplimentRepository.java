package team1.allo.emotioncard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.allo.emotioncard.entity.Compliment;

public interface ComplimentRepository extends JpaRepository<Compliment, Long> {

	List<Compliment> findByEmotionCardId(Long emotionCardId);
}
