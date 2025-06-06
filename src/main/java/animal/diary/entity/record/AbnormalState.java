package animal.diary.entity.record;

import lombok.Getter;

@Getter
public enum AbnormalState {
    INCONTINENCE("배변실수"),
    DROOLING("침흘림"),
    UNCONSCIOUS("의식없음"),
    NORMAL("이상없음");

    private final String description;

    AbnormalState(String description) {
        this.description = description;
    }

}