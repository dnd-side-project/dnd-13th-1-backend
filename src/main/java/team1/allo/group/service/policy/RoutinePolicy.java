package team1.allo.group.service.policy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public enum RoutinePolicy {
	NONE {
		@Override
		public boolean shouldAdd(LocalDate startDate, LocalDate current, List<DayOfWeek> targetDays) {
			// NONE → startDate 1건만 등록 (요일 무시)
			return current.equals(startDate);
		}
	},
	EVERY_DAY {
		@Override
		public boolean shouldAdd(LocalDate startDate, LocalDate current, List<DayOfWeek> targetDays) {
			// targetDays 비어있으면 저장 안 함
			return !targetDays.isEmpty() && targetDays.contains(current.getDayOfWeek());
		}
	},
	EVERY_WEEK {
		@Override
		public boolean shouldAdd(LocalDate startDate, LocalDate current, List<DayOfWeek> targetDays) {
			return isTargetDay(current, targetDays) &&
				ChronoUnit.WEEKS.between(startDate, current) % 1 == 0;
		}
	},
	BI_WEEK {
		@Override
		public boolean shouldAdd(LocalDate startDate, LocalDate current, List<DayOfWeek> targetDays) {
			return isTargetDay(current, targetDays) &&
				ChronoUnit.WEEKS.between(startDate, current) % 2 == 0;
		}
	},
	EVERY_MONTH {
		@Override
		public boolean shouldAdd(LocalDate startDate, LocalDate current, List<DayOfWeek> targetDays) {
			return isTargetDay(current, targetDays) &&
				ChronoUnit.MONTHS.between(startDate, current) % 1 == 0;
		}
	},
	THREE_MONTH {
		@Override
		public boolean shouldAdd(LocalDate startDate, LocalDate current, List<DayOfWeek> targetDays) {
			return isTargetDay(current, targetDays) &&
				ChronoUnit.MONTHS.between(startDate, current) % 3 == 0;
		}
	},
	SIX_MONTH {
		@Override
		public boolean shouldAdd(LocalDate startDate, LocalDate current, List<DayOfWeek> targetDays) {
			return isTargetDay(current, targetDays) &&
				ChronoUnit.MONTHS.between(startDate, current) % 6 == 0;
		}
	};

	public abstract boolean shouldAdd(LocalDate startDate, LocalDate current, List<DayOfWeek> targetDays);

	protected boolean isTargetDay(LocalDate current, List<DayOfWeek> targetDays) {
		return !targetDays.isEmpty() && targetDays.contains(current.getDayOfWeek());
	}
}
