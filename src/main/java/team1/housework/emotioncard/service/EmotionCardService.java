package team1.housework.emotioncard.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team1.housework.emotioncard.entity.EmotionCard;
import team1.housework.emotioncard.repository.EmotionCardRepository;
import team1.housework.emotioncard.service.dto.EmotionCardSaveRequest;
import team1.housework.emotioncard.service.dto.EmotionCardSaveResponse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmotionCardService {

	private final EmotionCardRepository emotionCardRepository;

	@Transactional
	public EmotionCardSaveResponse save(Long senderId, EmotionCardSaveRequest request) {
		EmotionCard emotionCard = request.toEntity(senderId);
		emotionCard = emotionCardRepository.save(emotionCard);
		return new EmotionCardSaveResponse(emotionCard.getId());
	}
}
