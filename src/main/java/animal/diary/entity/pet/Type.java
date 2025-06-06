package animal.diary.entity.pet;

import animal.diary.exception.InvalidNeuteredException;
import animal.diary.exception.InvalidTypeException;

import java.util.Arrays;

public enum Type {
    CAT, DOG;

    public static Type fromString(String value) {
        return Arrays.stream(Type.values())
                .filter(g -> g.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new InvalidTypeException("Invalid Type value: " + value));
    }
}
