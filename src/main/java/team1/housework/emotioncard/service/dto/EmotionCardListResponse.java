package team1.housework.emotioncard.service.dto;

import java.time.LocalDateTime;
import java.util.List;

public record EmotionCardListResponse(
	Long emotionCardId,
	String houseWorkName,
	List<String> compliments,
	String content,
	String senderNickName,
	String receiverNickName,
	LocalDateTime createdTime
) {
}
