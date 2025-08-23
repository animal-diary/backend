package animal.diary.dto.record;

import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.Convulsion;
import animal.diary.entity.record.state.AbnormalState;
import animal.diary.entity.record.state.BinaryState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@Schema(description = "경련 상태 기록 DTO")
public class ConvulsionRecordDTO {
    @NotNull(message = "펫 ID는 필수입니다.")
    @Schema(description = "펫 ID", example = "1")
    private Long petId;

    // 경련 했는지/안했는지
    @Schema(description = "경련 상태 (O: 있음, X: 없음)", example = "O")
    @NotNull(message = "경련 상태는 필수입니다.")
    private String state;

    // 경련 상태
    @Schema(description = "상태 값 - INCONTINENCE(배변실수), DROOLING(침흘림), UNCONSCIOUS(의식없음), NORMAL(추가 증상 없음)", example = "[\"INCONTINENCE\", \"DROOLING\"]")
    private List<String> abnormalState;


    @AssertTrue(message = "경련이 있을 때는 비정상 상태가 필수입니다.")
    private boolean isAbnormalStateValidWhenConvulsionExists() {
        if (state != null && "O".equalsIgnoreCase(state.trim())) {
            return abnormalState != null && !abnormalState.isEmpty();
        }
        return true;
    }

    @AssertTrue(message = "경련이 없으면 비정상 상태를 입력할 수 없습니다.")
    private boolean isAbnormalStateEmptyWhenNoConvulsion() {
        if (state != null && "X".equalsIgnoreCase(state.trim())) {
            return abnormalState == null || abnormalState.isEmpty();
        }
        return true;
    }

    public static Convulsion toEntity(ConvulsionRecordDTO dto, Pet pet, String videoUrl) {
        List<AbnormalState> abnormalStates = null;
        if (dto.getAbnormalState() != null && !dto.getAbnormalState().isEmpty()) {
            abnormalStates = dto.getAbnormalState().stream()
                    .map(AbnormalState::fromString)
                    .toList();
        }

        return Convulsion.builder()
                .state(BinaryState.fromString(dto.getState()))
                .abnormalState(abnormalStates)
                .pet(pet)
                .videoUrl(videoUrl)
                .build();
    }
}