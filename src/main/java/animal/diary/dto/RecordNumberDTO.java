package animal.diary.dto;

import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.Diary;
import animal.diary.entity.record.Energy;
import animal.diary.entity.record.LevelState;
import animal.diary.entity.record.Weight;
import animal.diary.exception.InvalidStateException;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RecordNumberDTO {

    private Long petId;
    private float weight;
    private Integer count;
    private String state;

    public static Weight toWeightEntity(RecordNumberDTO dto, Pet pet) {
        return Weight.builder()
                .weight(dto.getWeight())
                .pet(pet)
                .build();
    }

    public static Energy toEnergyEntity(RecordNumberDTO dto, Pet pet) {
        return Energy.builder()
                .state(LevelState.fromString(dto.getState(),() -> new InvalidStateException("상태 잘못됨")))
                .pet(pet)
                .build();
    }
}
