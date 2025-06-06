package animal.diary.entity.pet;

import lombok.Getter;

@Getter
public enum Disease {
    CARDIOVASCULAR("심혈관계"),
    RESPIRATORY("호흡계"),
    URINARY("비뇨계"),
    SKELETAL("골격계"),
    DERMATOLOGICAL("피부계"),
    NEUROLOGICAL("신경계"),
    DIGESTIVE("소화기계"),
    ENDOCRINE("내분비계");

    private final String koreanName;

    Disease(String koreanName) {
        this.koreanName = koreanName;
    }

}