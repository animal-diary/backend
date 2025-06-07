package animal.diary.dto;

import animal.diary.entity.record.*;
import animal.diary.exception.InvalidStateException;
import animal.diary.entity.pet.Pet;
import lombok.Builder;
import lombok.Data;

import java.util.function.Supplier;

@Builder
@Data
public class RecordNumberDTO {

    private Long petId;
    private float weight;
    private Integer count;
    private String title;
    private String content;
    private String state;

    public static Weight toWeightEntity(RecordNumberDTO dto, Pet pet) {
        return Weight.builder()
                .weight(dto.getWeight())
                .pet(pet)
                .build();
    }

    public static Energy toEnergyEntity(RecordNumberDTO dto, Pet pet) {
        return Energy.builder()
                .state(LevelState.fromString(dto.getState(), () -> new InvalidStateException("에너지 상태가 올바르지 않습니다.")))
                .pet(pet)
                .build();
    }

    public static Appetite toAppetiteEntity(RecordNumberDTO dto, Pet pet) {
        return Appetite.builder()
                .state(LevelState.fromString(dto.getState(), () -> new InvalidStateException("식욕 상태가 올바르지 않습니다.")))
                .pet(pet)
                .build();
    }

    public static Significant toSignificantEntity(RecordNumberDTO dto, Pet pet) {
        return Significant.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .pet(pet)
                .build();
    }
}
