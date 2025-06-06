package animal.diary.entity.pet;

import animal.diary.exception.InvalidGenderException;

import java.util.Arrays;

public enum Gender {
    MALE, FEMALE;

    public static Gender fromString(String value) {
        return Arrays.stream(Gender.values())
                .filter(g -> g.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new InvalidGenderException("Invalid gender value: " + value));
    }
}
