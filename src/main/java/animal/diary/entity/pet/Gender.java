package animal.diary.entity.pet;

import animal.diary.exception.InvalidGenderException;

import java.util.Arrays;
import java.util.Objects;

public enum Gender {
    MALE, FEMALE;

    public static Gender fromString(String value) {
        return Arrays.stream(Gender.values())
                .filter(g -> g.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new InvalidGenderException("Invalid gender value: " + value));
    }

    public static String getKoreanName(String value) {
        if (Objects.equals(value, MALE.name())) return "남아";
        else return "여아";
    }
}
