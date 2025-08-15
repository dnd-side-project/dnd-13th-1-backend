package team1.housework.emotioncard.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team1.housework.emotioncard.entity.Compliment;
import team1.housework.emotioncard.entity.EmotionCard;
import team1.housework.emotioncard.repository.ComplimentRepository;
import team1.housework.emotioncard.repository.EmotionCardRepository;
import team1.housework.emotioncard.service.dto.EmotionCardListRequest;
import team1.housework.emotioncard.service.dto.EmotionCardListResponse;
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
	private final ComplimentRepository complimentRepository;
	private final MemberService memberService;
	private final HouseWorkRepository houseWorkRepository;

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

		String houseWorkName = houseWorkRepository.findById(emotionCard.getHouseWorkId())
			.orElseThrow(() -> new NoSuchElementException("HouseWork does not exist"))
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

		List<EmotionCardListResponse> responses = emotionCardRepository.getAllWithCondition(memberId, filter, sorted);
		for (EmotionCardListResponse response : responses) {
			Long emotionCardId = response.emotionCardId();

			String newContent = complimentRepository.findByEmotionCardId(emotionCardId).stream()
				.map(Compliment::getContent)
				.collect(Collectors.joining(" "));

			if (!newContent.isEmpty()) {
				responses.set(responses.indexOf(response), new EmotionCardListResponse(
					response.emotionCardId(),
					response.houseWorkName(),
					newContent,
					response.senderNickName(),
					response.receiverNickName(),
					response.createdTime()
				));
			}
		}
		return responses;
	}
}
