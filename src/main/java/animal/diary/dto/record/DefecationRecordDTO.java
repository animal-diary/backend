package animal.diary.dto.record;

import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.Defecation;
import animal.diary.entity.record.state.LevelState;
import animal.diary.entity.record.state.StoolState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DefecationRecordDTO {
    @NotNull(message = "펫 ID는 필수입니다.")
    @Schema(description = "펫 ID", example = "1")
    private Long petId;

    // 배변량
    @NotNull(message = "배변량은 필수입니다.")
    @Schema(description = "배변량 NONE(없음), LOW(적음), NORMAL(보통), HIGH(많음)", example = "NORMAL")
    private String level;
    
    @Schema(description = "대변 상태 NORMAL(정상), DIARRHEA(설사), BLACK(검은변), BLOODY(혈변), ETC(기타)", example = "NORMAL")
    private String state;

    @Schema(description = "메모", example = "평소보다 단단한 편")
    @Size(max = 200, message = "메모는 200자 이내여야 합니다.")
    private String memo;

    @AssertTrue(message = "배변량이 있을 때는 대변 상태가 필수입니다.")
    private boolean isStateValidWhenLevelExists() {
        if (level != null && !"NONE".equalsIgnoreCase(level.trim())) {
            return state != null && !state.trim().isEmpty();
        }
        return true;
    }

    @AssertTrue(message = "배변량이 없으면 대변 상태는 입력할 수 없습니다.")
    private boolean isStateEmptyWhenLevelIsNone() {
        if (level != null && "NONE".equalsIgnoreCase(level.trim())) {
            return state == null || state.trim().isEmpty();
        }
        return true;
    }

    @AssertTrue(message = "배변량이 없으면 메모는 입력할 수 없습니다.")
    private boolean isMemoEmptyWhenLevelIsNone() {
        if (level != null && "NONE".equalsIgnoreCase(level.trim())) {
            return memo == null || memo.trim().isEmpty();
        }
        return true;
    }

    public static Defecation toEntity(DefecationRecordDTO dto, Pet pet, List<String> imageUrls) {
        StoolState stoolState = null;
        if (dto.getState() != null && !dto.getState().trim().isEmpty()) {
            stoolState = StoolState.fromString(dto.getState());
        }

        String memo = null;
        if (dto.getMemo() != null && !dto.getMemo().trim().isEmpty()) {
            memo = dto.getMemo();
        }

        return Defecation.builder()
                .level(LevelState.fromString(dto.getLevel()))
                .state(stoolState)
                .memo(memo)
                .pet(pet)
                .imageUrls(imageUrls)
                .build();
    }
}