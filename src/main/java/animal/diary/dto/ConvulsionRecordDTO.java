package animal.diary.dto;

import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.Convulsion;
import animal.diary.entity.record.state.AbnormalState;
import animal.diary.entity.record.state.BinaryState;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "상태 값 - INCONTINENCE(배변실수), DROOLING(침흘림), UNCONSCIOUS(의식없음), NORMAL(이상없음)", example = "[\"INCONTINENCE\", \"DROOLING\"]")
    @NotNull(message = "비정상 상태는 필수입니다.")
    private List<String> abnormalState;

    public static Convulsion toEntity(ConvulsionRecordDTO dto, Pet pet, String imageUrl) {
        return Convulsion.builder()
                .state(BinaryState.fromString(dto.getState()))
                .abnormalState(dto.getAbnormalState() != null ?
                    dto.getAbnormalState().stream()
                        .map(AbnormalState::fromString)
                        .toList()
                        : List.of())
                .pet(pet)
                .imageUrl(imageUrl)
                .build();
    }
}