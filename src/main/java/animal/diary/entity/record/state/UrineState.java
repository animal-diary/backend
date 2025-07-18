package animal.diary.entity.record.state;

import animal.diary.exception.InvalidStateException;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Supplier;

@Getter
public enum UrineState {
    NONE("무뇨"),
    BLOODY("혈뇨"),
    LIGHT("연함"),
    DARK("진함"),
    NORMAL("보통"),
    BAD_SMELL("악취");

    private final String description;
    UrineState(String description) {
        this.description = description;
    }

    // Default version with fixed exception type
    public static UrineState fromString(String value) {
        return fromString(value, () -> new InvalidStateException("Invalid UrineState value: " + value));
    }

    // Version that accepts custom exception supplier
    public static UrineState fromString(String value, Supplier<? extends RuntimeException> exceptionSupplier) {
        return Arrays.stream(UrineState.values())
                .filter(state -> state.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(exceptionSupplier);
    }
}