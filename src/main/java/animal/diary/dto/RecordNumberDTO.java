package animal.diary.dto;

import animal.diary.entity.record.*;
import animal.diary.exception.InvalidStateException;
import animal.diary.entity.pet.Pet;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.Data;

import java.util.function.Supplier;

@Builder
@Data
public class RecordNumberDTO {

    @NotNull(groups = {Default.class, WeightGroup.class, CountGroup.class, StateGroup.class},
            message = "펫 ID는 필수입니다.")
    private Long petId;
    @NotNull(groups = WeightGroup.class, message = "몸무게는 필수입니다.")
    private Float weight;
    @NotNull(groups = CountGroup.class, message = "횟수는 필수입니다.")
    private Integer count;
    @NotNull(groups = StateGroup.class, message = "상태는 필수입니다.")
    private String state;
    @NotNull(groups = BinaryStateGroup.class, message = "기절 상태는 필수입니다.")
    private String binaryState;


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

    public static RespiratoryRate toRespiratoryRateEntity(RecordNumberDTO dto, Pet pet) {
        return RespiratoryRate.builder()
                .count(dto.getCount())
                .pet(pet)
                .build();
    }

    public static HeartRate toHeartRateEntity(RecordNumberDTO dto, Pet pet) {
        return HeartRate.builder()
                .count(dto.getCount())
                .pet(pet)
                .build();
    }

    public static Syncope toSyncopeEntity(RecordNumberDTO dto, Pet pet) {
        return Syncope.builder()
                .state(BinaryState.fromString(dto.getBinaryState(), () -> new InvalidStateException("기절 상태가 올바르지 않습니다.")))
                .pet(pet)
                .build();
    }
}
