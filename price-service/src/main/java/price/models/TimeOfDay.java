package price.models;

import java.time.LocalTime;

import lombok.Getter;

@Getter
public enum TimeOfDay {
    MORNING(LocalTime.of(6, 0), LocalTime.of(12, 0)),
    DAY(LocalTime.of(12, 0), LocalTime.of(18, 0)),
    EVENING(LocalTime.of(18, 0), LocalTime.of(22, 0)),
    NIGHT(LocalTime.of(22, 0), LocalTime.of(6, 0));

    private final LocalTime startTime;
    private final LocalTime endTime;

    TimeOfDay(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}