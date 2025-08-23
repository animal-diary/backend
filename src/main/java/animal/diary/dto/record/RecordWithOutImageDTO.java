package animal.diary.dto.record;

import animal.diary.dto.*;
import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.*;
import animal.diary.entity.record.state.BinaryState;
import animal.diary.entity.record.state.LevelState;
import animal.diary.entity.record.state.UrineState;
import animal.diary.exception.InvalidStateException;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RecordWithOutImageDTO {

    @Builder
    @Getter
    public static class WeightRecordDTO {
        @NotNull(message = "펫 ID는 필수입니다.")
        @Schema(description = "펫 ID", example = "1")
        private Long petId;
        @NotNull(groups = WeightGroup.class, message = "몸무게는 필수입니다.")
        @Schema(description = "몸무게 (kg)", example = "5.0")
        private Float weight;

        public static Weight toWeightEntity(WeightRecordDTO dto, Pet pet) {
            return Weight.builder()
                    .weight(dto.getWeight())
                    .pet(pet)
                    .build();
        }
    }

    @Builder
    @Getter
    public static class EnergyAndAppetiteRecord {

        @NotNull(message = "펫 ID는 필수입니다.")
        @Schema(description = "펫 ID", example = "1")
        private Long petId;

        @NotNull(groups = StateGroup.class, message = "상태는 필수입니다.")
        @Schema(description = "가능한 값: LOW, NORMAL, HIGH", example = "NORMAL")
        private String state;

        public static Energy toEnergyEntity(EnergyAndAppetiteRecord dto, Pet pet) {
            return Energy.builder()
                    .state(LevelState.fromString(dto.getState(), () -> new InvalidStateException("에너지 상태가 올바르지 않습니다.")))
                    .pet(pet)
                    .build();
        }

        public static Appetite toAppetiteEntity(EnergyAndAppetiteRecord dto, Pet pet) {
            return Appetite.builder()
                    .state(LevelState.fromString(dto.getState(), () -> new InvalidStateException("식욕 상태가 올바르지 않습니다.")))
                    .pet(pet)
                    .build();
        }
    }

    @Builder
    @Getter
    public static class RespiratoryRateAndHeartRateRecord {
        @NotNull(message = "펫 ID는 필수입니다.")
        @Schema(description = "펫 ID", example = "1")
        private Long petId;

        @NotNull(groups = CountGroup.class, message = "호흡수/심박수는 필수입니다.")
        @Schema(description = "호흡수 (회/분)", example = "20")
        private Integer count;

        public static RespiratoryRate toRespiratoryRateEntity(RespiratoryRateAndHeartRateRecord dto, Pet pet) {
            return RespiratoryRate.builder()
                    .count(dto.getCount())
                    .pet(pet)
                    .build();
        }

        public static HeartRate toHeartRateEntity(RespiratoryRateAndHeartRateRecord dto, Pet pet) {
            return HeartRate.builder()
                    .count(dto.getCount())
                    .pet(pet)
                    .build();
        }
    }

    @Builder
    @Getter
    public static class SyncopeRecord {
        @NotNull(message = "펫 ID는 필수입니다.")
        @Schema(description = "펫 ID", example = "1")
        private Long petId;

        @NotNull(groups = BinaryStateGroup.class, message = "기절 상태는 필수입니다.")
        @Schema(description = "기절 상태 (O: 있음, X: 없음)", example = "X")
        private String binaryState;

        public static Syncope toSyncopeEntity(SyncopeRecord dto, Pet pet) {
            return Syncope.builder()
                    .state(BinaryState.fromString(dto.getBinaryState(), () -> new InvalidStateException("기절 상태가 올바르지 않습니다.")))
                    .pet(pet)
                    .build();
        }
    }


    @Builder
    @Getter
    public static class UrinaryRecord {
        @NotNull(message = "펫 ID는 필수입니다.")
        @Schema(description = "펫 ID", example = "1")
        private Long petId;

        @NotNull(groups = UrineGroup.class, message = "소변 상태는 필수입니다.")
        @Schema(description = "소변 상태 (BLOODY, LIGHT, DARK, NORMAL, ETC)", example = "NORMAL")
        private String urineState;

        @NotNull(groups = UrineGroup.class, message = "소변량은 필수입니다.")
        @Schema(description = "소변량 (NONE, LOW, NORMAL, HIGH)", example = "NORMAL")
        private String urineAmount;

        @NotNull(groups = UrineGroup.class, message = "소변 악취 상태는 필수입니다.")
        @Schema(description = "소변 악취 상태 (O: 있음, X: 없음)", example = "X")
        private String binaryState;

        public static Urinary toUrinaryEntity(UrinaryRecord dto, Pet pet) {
            return Urinary.builder()
                    .state(UrineState.fromString(dto.getUrineState(), () -> new InvalidStateException("소변 상태가 올바르지 않습니다.")))
                    .output(LevelState.fromString(dto.getUrineAmount(), () -> new InvalidStateException("소변량 상태가 올바르지 않습니다.")))
                    .binaryState(BinaryState.fromString(dto.getBinaryState(), () -> new InvalidStateException("소변 소량 상태가 올바르지 않습니다.")))
                    .pet(pet)
                    .build();
        }
    }
}
