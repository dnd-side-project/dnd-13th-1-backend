package team1.allo.emotioncard.repository;

import java.util.List;

import team1.allo.emotioncard.service.dto.EmotionCardListResponse;

public interface EmotionCardCustomRepository {

	List<EmotionCardListResponse> getAllWithCondition(Long memberId, String filter, String sorted);

	Long countEmotionCardSentByMember(Long memberId);

	Long countEmotionCardReceivedByMember(Long memberId);
}
