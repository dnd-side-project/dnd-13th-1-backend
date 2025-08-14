package team1.housework.emotioncard.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import team1.housework.emotioncard.entity.EmotionCard;

public record EmotionCardResponse(
	Long emotionCardId,
	String houseWorkName,
	List<String> compliments,
	String disappointment,
	String senderNickName,
	String receiverNickName,
	LocalDateTime createdTime
) {

	public static EmotionCardResponse from(
		EmotionCard emotionCard,
		String houseWorkName,
		String senderNickName,
		String receiverNickName
	) {
		return new EmotionCardResponse(
			emotionCard.getId(),
			houseWorkName,
			emotionCard.getCompliments(),
			emotionCard.getDisappointment(),
			senderNickName,
			receiverNickName,
			emotionCard.getCreatedDate()
		);
	}
}
