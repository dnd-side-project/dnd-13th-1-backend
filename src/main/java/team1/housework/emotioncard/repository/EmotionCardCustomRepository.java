package team1.housework.emotioncard.repository;

import java.util.List;

import team1.housework.emotioncard.service.dto.EmotionCardListResponse;

public interface EmotionCardCustomRepository {

	List<EmotionCardListResponse> getAllWithCondition(Long memberId, String filter, String sorted);
}
