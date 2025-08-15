package team1.housework.emotioncard.entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	private LocalDateTime createdDate;

	public EmotionCard(
		Long senderId,
		Long receiverId,
		Long houseWorkId,
		String disappointment
	) {
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.houseWorkId = houseWorkId;
		this.disappointment = disappointment;
		this.createdDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}
}
