package team1.housework.group.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HouseWork {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "place_id")
	private Place place;

	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;

	private LocalDate taskDate;

	private boolean isNotified;

	public HouseWork(String name, Place place, Group group, LocalDate taskDate, boolean isNotified) {
		this.name = name;
		this.place = place;
		this.group = group;
		this.taskDate = taskDate;
		this.isNotified = isNotified;
	}
}
