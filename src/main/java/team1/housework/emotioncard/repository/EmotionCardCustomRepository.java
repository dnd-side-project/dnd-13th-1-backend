package team1.housework.emotioncard.repository;

import java.util.Optional;

import team1.housework.emotioncard.entity.EmotionCard;

public interface EmotionCardCustomRepository {

	Optional<EmotionCard> findByIdWithFetchJoin(Long id);
}
