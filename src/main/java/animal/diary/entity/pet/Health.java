package animal.diary.entity.pet;

import animal.diary.exception.InvalidHealthException;

import java.util.Arrays;

public enum Health {
    GOOD, BAD, UNKNOWN;

    public static Health fromString(String value) {
        return Arrays.stream(Health.values())
                .filter(g -> g.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new InvalidHealthException("Invalid health value: " + value));
    }
}
