package animal.diary.dto.request;

import animal.diary.entity.record.state.LevelState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "식욕 기록 필터링 요청 DTO")
public class AppetiteFilterRequestDTO {

    @Schema(description = "식욕 상태 (NONE, LOW, NORMAL, HIGH)", example = "NORMAL", nullable = true)
    private LevelState state;
}