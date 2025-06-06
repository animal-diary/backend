package animal.diary.entity.pet;

import animal.diary.exception.DiseaseInvalidException;
import lombok.Getter;

import java.util.Arrays;

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

    public static Disease fromString(String value) {
        return Arrays.stream(Disease.values())
                .filter(d -> d.name().equalsIgnoreCase(value) || d.koreanName.equals(value))
                .findFirst()
                .orElseThrow(() -> new DiseaseInvalidException("Invalid disease value: " + value));
    }
}