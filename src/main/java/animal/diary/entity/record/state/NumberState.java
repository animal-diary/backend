package animal.diary.entity.record.state;

import lombok.Getter;

@Getter
public enum NumberState {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3);

    private final int value;

    NumberState(int value) {
        this.value = value;
    }

    public static NumberState fromString(String state) {
        try {
            int intValue = Integer.parseInt(state.trim());
            for (NumberState numberState : NumberState.values()) {
                if (numberState.getValue() == intValue) {
                    return numberState;
                }
            }
            throw new IllegalArgumentException("Invalid NumberState value: " + state);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid NumberState value: " + state, e);
        }
    }
}
