package animal.diary.dto.record;

import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.Skin;
import animal.diary.entity.record.state.NumberState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@Schema(description = "펫의 피부 상태 기록 DTO")
public class SkinRecordDTO {
    @NotNull(message = "펫 ID는 필수입니다.")
    @Schema(description = "펫 ID", example = "1")
    private Long petId;
    
    @NotNull(message = "피부 상태는 필수입니다.")
    @Schema(description = "피부 상태 (0: 정상, 1: 약간의 문제, 2: 중간 정도의 문제, 3: 심각한 문제)", example = "0")
    private String state;

    @Schema(description = "메모", example = "털이 많이 빠지고 있음")
    @Size(max = 200, message = "메모는 200자 이내여야 합니다.")
    private String memo;

    public static Skin toEntity(SkinRecordDTO dto, Pet pet, List<String> imageUrls) {
        return Skin.builder()
                .state(NumberState.fromString(dto.getState()))
                .memo(dto.getMemo())
                .pet(pet)
                .imageUrls(imageUrls)
                .build();
    }
}