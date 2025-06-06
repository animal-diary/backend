package animal.diary.entity.pet;

import animal.diary.exception.InvalidHealthException;
import animal.diary.exception.InvalidNeuteredException;

import java.util.Arrays;

public enum Neutered {
    O, X;

    public static Neutered fromString(String value) {
        return Arrays.stream(Neutered.values())
                .filter(g -> g.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new InvalidNeuteredException("Invalid Neutered value: " + value));
    }
}
