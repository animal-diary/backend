package animal.diary.util;

import animal.diary.entity.record.state.AbnormalState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class AbnormalStateConverter implements AttributeConverter<List<AbnormalState>, String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<AbnormalState> attribute) {
        if (attribute == null || attribute.isEmpty()) return "";
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<AbnormalState> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) return Collections.emptyList();
        return Arrays.stream(dbData.split(DELIMITER))
                .map(String::trim)
                .map(AbnormalState::valueOf)
                .collect(Collectors.toList());
    }
}
