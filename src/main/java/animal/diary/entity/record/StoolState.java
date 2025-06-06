package animal.diary.entity.record;

import lombok.Getter;

@Getter
public enum StoolState {
    NORMAL("정상"),
    DIARRHEA("설사"),
    BLACK("검은변"),
    BLOODY("혈변");

    private final String description;

    StoolState(String description) {
        this.description = description;
    }
}