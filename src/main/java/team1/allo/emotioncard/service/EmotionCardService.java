package team1.allo.emotioncard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team1.allo.emotioncard.entity.Compliment;
import team1.allo.emotioncard.entity.EmotionCard;
import team1.allo.emotioncard.repository.ComplimentRepository;
import team1.allo.emotioncard.repository.EmotionCardRepository;
import team1.allo.emotioncard.service.dto.EmotionCardListDto;
import team1.allo.emotioncard.service.dto.EmotionCardListRequest;
import team1.allo.emotioncard.service.dto.EmotionCardListResponse;
import team1.allo.emotioncard.service.dto.EmotionCardResponse;
import team1.allo.emotioncard.service.dto.EmotionCardSaveRequest;
import team1.allo.emotioncard.service.dto.EmotionCardSaveResponse;
import team1.allo.housework.service.HouseWorkService;
import team1.allo.member.service.MemberService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmotionCardService {

	private static final String THANK_EMOTION_TYPE = "thank";
	private static final String REGRETFUL_EMOTION_TYPE = "regretful";
	private static final String BOTH_EMOTION_TYPE = "both";

	private final EmotionCardRepository emotionCardRepository;
	private final ComplimentRepository complimentRepository;
	private final MemberService memberService;
	private final HouseWorkService houseWorkService;

	@Transactional
	public EmotionCardSaveResponse save(Long senderId, EmotionCardSaveRequest request) {
		EmotionCard emotionCard = request.toEntity(senderId);
		final EmotionCard savedEmotionCard = emotionCardRepository.save(emotionCard);

		List<Compliment> compliments = request.compliments().stream()
			.map(content -> new Compliment(savedEmotionCard, content))
			.toList();
		complimentRepository.saveAll(compliments);

		return new EmotionCardSaveResponse(emotionCard.getId());
	}

	public EmotionCardResponse getEmotionCard(Long emotionCardId) {
		EmotionCard emotionCard = emotionCardRepository.findById(emotionCardId)
			.orElseThrow(() -> new NoSuchElementException("EmotionCard does not exist"));
		List<String> compliments = complimentRepository.findByEmotionCardId(emotionCardId).stream()
			.map(Compliment::getContent)
			.toList();

		String houseWorkName = houseWorkService.findById(emotionCard.getHouseWorkId())
			.getName();
		String senderNickName = memberService.findById(emotionCard.getSenderId())
			.getName();
		String receiverNickName = memberService.findById(emotionCard.getReceiverId())
			.getName();

		return EmotionCardResponse.from(
			emotionCard,
			compliments,
			houseWorkName,
			senderNickName,
			receiverNickName
		);
	}

	public List<EmotionCardListResponse> getMyEmotionCards(Long memberId, EmotionCardListRequest request) {
		String filter = request.filter();
		String sorted = request.sorted();

		List<EmotionCardListResponse> responses = new ArrayList<>();
		List<EmotionCardListDto> cardList = emotionCardRepository.getAllWithCondition(memberId, filter, sorted);
		for (EmotionCardListDto card : cardList) {
			Long emotionCardId = card.emotionCardId();

			String newContent = complimentRepository.findByEmotionCardId(emotionCardId).stream()
				.map(Compliment::getContent)
				.collect(Collectors.joining(" "));
			String disappointment = card.content();

			String emotionType = BOTH_EMOTION_TYPE;
			EmotionCardListResponse response = EmotionCardListResponse.from(card, newContent, emotionType);
			if (!newContent.isEmpty() && disappointment == null) {
				emotionType = THANK_EMOTION_TYPE;
				response = EmotionCardListResponse.from(card, newContent, emotionType);
			}
			if (newContent.isEmpty() && disappointment != null) {
				emotionType = REGRETFUL_EMOTION_TYPE;
				response = EmotionCardListResponse.from(card, disappointment, emotionType);
			}
			responses.add(response);
		}
		return responses;
	}
}
