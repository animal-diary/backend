package animal.diary.entity.record;

import animal.diary.exception.InvalidStateException;

import java.util.Arrays;
import java.util.function.Supplier;

public enum BinaryState {
    O, X;

    // Default version with fixed exception type
    public static BinaryState fromString(String value) {
        return fromString(value, () -> new InvalidStateException("Invalid BinaryState value: " + value));
    }

    // Version that accepts custom exception supplier
    public static BinaryState fromString(String value, Supplier<? extends RuntimeException> exceptionSupplier) {
        return Arrays.stream(BinaryState.values())
                .filter(state -> state.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(exceptionSupplier);
    }
}
