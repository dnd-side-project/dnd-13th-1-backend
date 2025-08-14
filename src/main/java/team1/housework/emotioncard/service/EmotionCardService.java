package team1.housework.emotioncard.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team1.housework.emotioncard.entity.EmotionCard;
import team1.housework.emotioncard.repository.EmotionCardRepository;
import team1.housework.emotioncard.service.dto.EmotionCardResponse;
import team1.housework.emotioncard.service.dto.EmotionCardSaveRequest;
import team1.housework.emotioncard.service.dto.EmotionCardSaveResponse;
import team1.housework.group.repository.housework.HouseWorkRepository;
import team1.housework.member.service.MemberService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmotionCardService {

	private final EmotionCardRepository emotionCardRepository;
	private final MemberService memberService;
	private final HouseWorkRepository houseWorkRepository;

	@Transactional
	public EmotionCardSaveResponse save(Long senderId, EmotionCardSaveRequest request) {
		EmotionCard emotionCard = request.toEntity(senderId);
		emotionCard = emotionCardRepository.save(emotionCard);
		return new EmotionCardSaveResponse(emotionCard.getId());
	}

	public EmotionCardResponse getEmotionCard(Long emotionCardId) {
		EmotionCard emotionCard = emotionCardRepository.findByIdWithFetchJoin(emotionCardId)
			.orElseThrow(() -> new NoSuchElementException("EmotionCard does not exist"));

		String houseWorkName = houseWorkRepository.findById(emotionCard.getHouseWorkId())
			.orElseThrow(() -> new NoSuchElementException("HouseWork does not exist"))
			.getName();
		String senderNickName = memberService.findById(emotionCard.getSenderId())
			.getName();
		String receiverNickName = memberService.findById(emotionCard.getReceiverId())
			.getName();

		return EmotionCardResponse.from(
			emotionCard,
			houseWorkName,
			senderNickName,
			receiverNickName
		);
	}
}
