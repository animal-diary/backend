package animal.diary.entity.record;

import lombok.Getter;

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
}