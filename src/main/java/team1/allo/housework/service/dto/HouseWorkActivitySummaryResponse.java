package team1.allo.housework.service.dto;

public record HouseWorkActivitySummaryResponse(
	Long receivedEmotionCardCount,
	Long sentEmotionCardCount,
	Long completedHouseWorkCount
) {
}
