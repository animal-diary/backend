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
}
