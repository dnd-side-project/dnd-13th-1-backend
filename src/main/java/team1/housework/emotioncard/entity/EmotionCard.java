package team1.housework.emotioncard.entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmotionCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long senderId;

	private Long receiverId;

	private Long houseWorkId;

	private String disappointment;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
		name = "emotion_card_compliment",
		joinColumns = @JoinColumn(name = "emotion_card_id")
	)
	@Column(name = "compliment")
	@OrderColumn(name = "compliment_order")
	private List<String> compliments = new ArrayList<>();

	private LocalDateTime createdDate;

	public EmotionCard(
		Long senderId,
		Long receiverId,
		Long houseWorkId,
		String disappointment,
		List<String> compliments
	) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.houseWorkId = houseWorkId;
		this.disappointment = disappointment;
		this.compliments = compliments;
		this.createdDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}
}
