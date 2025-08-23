package animal.diary.dto.record;

import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.Snot;
import animal.diary.entity.record.state.SnotState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@Schema(description = "펫의 콧물 기록 DTO")
public class SnotRecordDTO {
    @Schema(description = "펫 ID", example = "1")
    @NotNull(message = "펫 ID는 필수입니다.")
    private Long petId;

    @Schema(description = "콧물 상태 - (CLEAR(맑음), MUCUS(탁함), BLOODY(피섞임))", example = "CLEAR")
    @NotNull(message = "콧물 상태는 필수입니다.")
    private String state;

    @Schema(description = "메모", example = "콧물이 조금 나와요.")
    @Size(max = 200, message = "메모는 200자 이내여야 합니다.")
    private String memo;

    public static Snot toEntity(SnotRecordDTO dto, Pet pet, List<String> imageUrls) {
        return Snot.builder()
                .state(SnotState.fromString(dto.getState()))
                .pet(pet)
                .memo(dto.getMemo() != null ? dto.getMemo() : null)
                .imageUrls(imageUrls)
                .build();
    }
}