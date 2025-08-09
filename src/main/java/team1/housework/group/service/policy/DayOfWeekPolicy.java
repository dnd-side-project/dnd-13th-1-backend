package team1.housework.group.service.policy;

import java.time.DayOfWeek;

public enum DayOfWeekPolicy {
	MONDAY(DayOfWeek.MONDAY),
	TUESDAY(DayOfWeek.TUESDAY),
	WEDNESDAY(DayOfWeek.WEDNESDAY),
	THURSDAY(DayOfWeek.THURSDAY),
	FRIDAY(DayOfWeek.FRIDAY),
	SATURDAY(DayOfWeek.SATURDAY),
	SUNDAY(DayOfWeek.SUNDAY);

	private final DayOfWeek dayOfWeek;

	DayOfWeekPolicy(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public DayOfWeek toJavaDayOfWeek() {
		return dayOfWeek;
	}
}
