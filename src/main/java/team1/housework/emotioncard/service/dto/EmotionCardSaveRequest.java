package team1.housework.emotioncard.service.dto;

import java.util.List;

import team1.housework.emotioncard.entity.EmotionCard;

public record EmotionCardSaveRequest(
	Long receiverId,
	Long houseWorkId,
	String disappointment,
	List<String> compliments
) {

	public EmotionCard toEntity(Long senderId) {
		return new EmotionCard(
			senderId,
			receiverId,
			houseWorkId,
			disappointment,
			compliments
		);
	}
}
