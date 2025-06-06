package animal.diary.dto;

import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.Diary;
import animal.diary.entity.record.Weight;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RecordNumberDTO {

    private Long petId;
    private float weight;
    private Integer count;

    public static Weight toWeightEntity(RecordNumberDTO dto, Pet pet) {
        return Weight.builder()
                .weight(dto.getWeight())
                .pet(pet)
                .build();
    }
}
