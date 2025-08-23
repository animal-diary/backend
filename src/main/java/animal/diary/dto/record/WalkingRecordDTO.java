package animal.diary.dto.record;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "펫의 걷는 모습 기록 DTO")
public class WalkingRecordDTO {
    @Schema(description = "펫 ID", example = "1")
    @NotNull(message = "펫 ID는 필수입니다.")
    private Long petId;

    @Schema(description = "제목", example = "첫 번째 산책")
    private String title;
}