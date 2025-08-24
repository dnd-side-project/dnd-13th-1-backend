package team1.allo.emotioncard.service.dto;

import java.time.LocalDateTime;

public record EmotionCardListResponse(
	Long emotionCardId,
	String houseWorkName,
	String content,
	String senderNickName,
	String receiverNickName,
	LocalDateTime createdTime,
	String emotionType
) {

	public static EmotionCardListResponse from(EmotionCardListDto dto, String newContent, String emotionType) {
		return new EmotionCardListResponse(
			dto.emotionCardId(),
			dto.houseWorkName(),
			newContent,
			dto.senderNickName(),
			dto.receiverNickName(),
			dto.createdTime(),
			emotionType
		);
	}
}
