package team1.allo.emotioncard.service.dto;

import java.time.LocalDateTime;

public record EmotionCardListDto(
	Long emotionCardId,
	String houseWorkName,
	String content,
	String senderNickName,
	String receiverNickName,
	LocalDateTime createdTime
) {
}
