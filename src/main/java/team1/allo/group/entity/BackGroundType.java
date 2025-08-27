package team1.allo.group.entity;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BackGroundType {
	ROOM_MATE(0),
	FAMILY(1),
	COUPLE(2);

	private final int number;

	public static BackGroundType valueOf(int number) {
		return Arrays.stream(values())
			.filter(type -> type.number == number)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("backGround not exists"));
	}

}
