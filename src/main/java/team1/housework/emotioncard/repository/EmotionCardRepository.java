package team1.housework.emotioncard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team1.housework.emotioncard.entity.EmotionCard;

public interface EmotionCardRepository extends JpaRepository<EmotionCard, Integer>, EmotionCardCustomRepository {
}
