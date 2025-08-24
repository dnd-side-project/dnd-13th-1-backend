package team1.allo.emotioncard.repository;

import java.util.List;

import team1.allo.emotioncard.service.dto.EmotionCardListDto;

public interface EmotionCardCustomRepository {

	List<EmotionCardListDto> getAllWithCondition(Long memberId, String filter, String sorted);

	Long countEmotionCardSentByMember(Long memberId);

	Long countEmotionCardReceivedByMember(Long memberId);
}
