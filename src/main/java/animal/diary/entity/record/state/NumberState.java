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

}
