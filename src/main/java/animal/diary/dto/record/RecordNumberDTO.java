package animal.diary.dto.record;

import animal.diary.dto.*;
import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.*;
import animal.diary.entity.record.state.BinaryState;
import animal.diary.entity.record.state.LevelState;
import animal.diary.entity.record.state.UrineState;
import animal.diary.exception.InvalidStateException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RecordNumberDTO {

    @NotNull(groups = {Default.class, WeightGroup.class, CountGroup.class, StateGroup.class, BinaryStateGroup.class, UrineGroup.class},
            message = "펫 ID는 필수입니다.")
    @Schema(description = "펫 ID", example = "1")
    private Long petId;

    @NotNull(groups = WeightGroup.class, message = "몸무게는 필수입니다.")
    @Schema(description = "몸무게 (kg)", example = "5.0")
    private Float weight;

    @NotNull(groups = CountGroup.class, message = "횟수는 필수입니다.")
    @Schema(description = "호흡수 또는 심박수 (회/분)", example = "20")
    private Integer count;

    @NotNull(groups = StateGroup.class, message = "상태는 필수입니다.")
    @Schema(description = "가능한 값: NONE, LOW, NORMAL, HIGH", example = "NORMAL")
    private String state;
    
    @NotNull(groups = BinaryStateGroup.class, message = "기절 상태는 필수입니다.")
    @Schema(description = "가능한 값: O(있음), X(없음)", example = "X")
    private String binaryState;
    
    @NotNull(groups = UrineGroup.class, message = "소변 상태는 필수입니다.")
    @Schema(description = "가능한 값: BLOODY, LIGHT, DARK, NORMAL", example = "NORMAL")
    private String urineState;
    
    @NotNull(groups = UrineGroup.class, message = "소변량은 필수입니다.")
    @Schema(description = "가능한 값: NONE, LOW, NORMAL, HIGH", example = "NORMAL")
    private String urineAmount;


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

    public static Urinary toUrinaryEntity(RecordNumberDTO dto, Pet pet) {
        return Urinary.builder()
                .state(UrineState.fromString(dto.getUrineState(), () -> new InvalidStateException("소변 상태가 올바르지 않습니다.")))
                .output(LevelState.fromString(dto.getUrineAmount(), () -> new InvalidStateException("소변량 상태가 올바르지 않습니다.")))
                .binaryState(BinaryState.fromString(dto.getBinaryState(), () -> new InvalidStateException("소변 소량 상태가 올바르지 않습니다.")))
                .pet(pet)
                .build();
    }
}
