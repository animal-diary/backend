package animal.diary.entity.record.state;

import animal.diary.exception.InvalidStateException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public enum SnotState {
    CLEAR("맑음"), MUCUS("탁함"), BLOODY("피섞임");
    private final String description;

    public static SnotState fromString(String value) {
        return fromString(value, () -> new InvalidStateException("Invalid AbnormalState value: " + value));
    }

    public static SnotState fromString(String value, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (value == null) {
            throw exceptionSupplier.get();
        }
        return Arrays.stream(SnotState.values())
                .filter(state -> state.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(exceptionSupplier);
    }
}
