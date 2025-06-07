package animal.diary.entity.pet;

import animal.diary.exception.InvalidTypeException;
import lombok.Getter;

import java.util.Arrays;
import java.util.Locale;

@Getter
public enum Type {
    CAT("고양이"),
    DOG("강아지");

    private final String korean;

    Type(String korean) {
        this.korean = korean;
    }

    public static Type fromString(String value) {
        String input = value.trim().toLowerCase(Locale.ROOT);

        return Arrays.stream(Type.values())
                .filter(type ->
                        type.name().equalsIgnoreCase(input) ||  // "CAT", "DOG"
                                type.korean.equalsIgnoreCase(input)     // "고양이", "강아지"
                )
                .findFirst()
                .orElseThrow(() -> new InvalidTypeException("Invalid Type value: " + value));
    }
}
