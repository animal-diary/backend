package animal.diary.entity.record.state;

import animal.diary.exception.InvalidStateException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public enum AbnormalState {
    INCONTINENCE("배변실수"),
    DROOLING("침흘림"),
    UNCONSCIOUS("의식없음"),
    NORMAL("추가 증상 없음");

    private final String description;

    public static AbnormalState fromString(String value) {
        return fromString(value, () -> new InvalidStateException("Invalid AbnormalState value: " + value));
    }

    public static AbnormalState fromString(String value, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (value == null) {
            throw exceptionSupplier.get();
        }
        return Arrays.stream(AbnormalState.values())
                .filter(state -> state.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(exceptionSupplier);
    }

}