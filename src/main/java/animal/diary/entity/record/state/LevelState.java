package animal.diary.entity.record.state;

import animal.diary.exception.InvalidHealthException;

import java.util.Arrays;
import java.util.function.Supplier;

public enum LevelState {
    NONE, LOW, NORMAL, HIGH;

    // 기본 버전 (예외 타입 고정)
    public static LevelState fromString(String value) {
        return fromString(value, () -> new InvalidHealthException("Invalid LevelState value: " + value));
    }

    // 예외를 직접 전달받는 버전
    public static LevelState fromString(String value, Supplier<? extends RuntimeException> exceptionSupplier) {
        return Arrays.stream(LevelState.values())
                .filter(g -> g.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(exceptionSupplier);
    }

    // 소변의 경우 NONE(무뇨), LOW(적음), NORMAL(보통), HIGH(많음)로 전달해야 함.
    public static String toUrineString(LevelState state) {
        if (state == NONE) {
            return "무뇨";
        } else if (state == LOW) {
            return "적음";
        } else if (state == NORMAL) {
            return "보통";
        } else if (state == HIGH) {
            return "많음";
        } else {
            throw new InvalidHealthException("Invalid LevelState value: " + state);
        }
    }
}
