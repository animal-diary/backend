package animal.diary.dto.record;

import animal.diary.dto.UrineGroup;
import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.Urinary;
import animal.diary.entity.record.state.BinaryState;
import animal.diary.entity.record.state.LevelState;
import animal.diary.entity.record.state.UrineState;
import animal.diary.exception.InvalidStateException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "펫의 소변 기록 DTO")
public class UrinaryRecordDTO {
    @NotNull(message = "펫 ID는 필수입니다.")
    @Schema(description = "펫 ID", example = "1")
    private Long petId;

    @NotNull(groups = UrineGroup.class, message = "소변량은 필수입니다.")
    @Schema(description = "소변량 (NONE(무뇨), LOW(적음), NORMAL(보통), HIGH(많음))", example = "NORMAL")
    private String urineAmount;

    @Schema(description = "소변 상태 (BLOODY(혈뇨), LIGHT(연함), DARK(진함), NORMAL(보통), ETC(기타))", example = "NORMAL")
    private String urineState;

    @Schema(description = "소변 악취 상태 (O: 있음, X: 없음)", example = "X")
    private String binaryState;

    @Schema(description = "메모", example = "소변이 자주 보임")
    @Size(max = 200, message = "메모는 200자 이내여야 합니다.")
    private String memo;

    @AssertTrue(groups = UrineGroup.class, message = "소변량이 있을 때는 소변 상태가 필수입니다.")
    private boolean isUrineStateValidWhenAmountExists() {
        if (urineAmount != null && !"NONE".equalsIgnoreCase(urineAmount.trim())) {
            return urineState != null && !urineState.trim().isEmpty();
        }
        return true;
    }

    @AssertTrue(groups = UrineGroup.class, message = "소변량이 있을 때는 소변 악취 상태가 필수입니다.")
    private boolean isBinaryStateValidWhenAmountExists() {
        if (urineAmount != null && !"NONE".equalsIgnoreCase(urineAmount.trim())) {
            return binaryState != null && !binaryState.trim().isEmpty();
        }
        return true;
    }

    @AssertTrue(groups = UrineGroup.class, message = "무뇨일 때는 소변 상태를 입력할 수 없습니다.")
    private boolean isUrineStateEmptyWhenAmountIsNone() {
        if (urineAmount != null && "NONE".equalsIgnoreCase(urineAmount.trim())) {
            return urineState == null || urineState.trim().isEmpty();
        }
        return true;
    }

    @AssertTrue(groups = UrineGroup.class, message = "무뇨일 때는 소변 악취 상태를 입력할 수 없습니다.")
    private boolean isBinaryStateEmptyWhenAmountIsNone() {
        if (urineAmount != null && "NONE".equalsIgnoreCase(urineAmount.trim())) {
            return binaryState == null || binaryState.trim().isEmpty();
        }
        return true;
    }

    @AssertTrue(groups = UrineGroup.class, message = "무뇨일 때는 메모를 입력할 수 없습니다.")
    private boolean isMemoEmptyWhenAmountIsNone() {
        if (urineAmount != null && "NONE".equalsIgnoreCase(urineAmount.trim())) {
            return memo == null || memo.trim().isEmpty();
        }
        return true;
    }

    public static Urinary toUrinaryEntity(UrinaryRecordDTO dto, Pet pet) {
        UrineState urineState = null;
        BinaryState urinaryBinaryState = null;
        String memo = null;

        if (dto.getUrineState() != null && !dto.getUrineState().trim().isEmpty()) {
            urineState = UrineState.fromString(dto.getUrineState(), () -> new InvalidStateException("소변 상태가 올바르지 않습니다."));
        }

        if (dto.getBinaryState() != null && !dto.getBinaryState().trim().isEmpty()) {
            urinaryBinaryState = BinaryState.fromString(dto.getBinaryState(), () -> new InvalidStateException("소변 악취 상태가 올바르지 않습니다."));
        }

        if (dto.getMemo() != null && !dto.getMemo().trim().isEmpty()) {
            memo = dto.getMemo();
        }

        return Urinary.builder()
                .state(urineState)
                .output(LevelState.fromString(dto.getUrineAmount(), () -> new InvalidStateException("소변량 상태가 올바르지 않습니다.")))
                .binaryState(urinaryBinaryState)
                .memo(memo)
                .pet(pet)
                .build();
    }
}
