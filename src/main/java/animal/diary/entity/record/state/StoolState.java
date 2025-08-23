package animal.diary.entity.record.state;

import lombok.Getter;

@Getter
public enum StoolState {
    NORMAL("정상"),
    DIARRHEA("설사"),
    BLACK("검은변"),
    BLOODY("혈변"),
    ETC("기타");

    private final String description;

    StoolState(String description) {
        this.description = description;
    }

    public static StoolState fromString(String state) {
        if (state == null || state.trim().isEmpty()) {
            return null; // 또는 예외를 던질 수도 있습니다.
        }
        for (StoolState stoolState : StoolState.values()) {
            if (stoolState.name().equalsIgnoreCase(state.trim())) {
                return stoolState;
            }
        }
        throw new IllegalArgumentException("Invalid StoolState value: " + state);
    }
}