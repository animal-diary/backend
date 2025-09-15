package animal.diary.dto.request;

import animal.diary.entity.record.state.LevelState;
import animal.diary.entity.record.state.StoolState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "배변 기록 필터링 요청 DTO")
public class DefecationFilterRequestDTO {

    @Schema(description = "배변량 (NONE, LOW, NORMAL, HIGH)", example = "NORMAL", nullable = true)
    private LevelState level;

    @Schema(description = "대변 상태 (NORMAL, DIARRHEA, BLACK, BLOODY, ETC)", example = "NORMAL", nullable = true)
    private StoolState state;

    @Schema(description = "메모/이미지 유무 (O, X 복수 선택 가능, 쉼표로 구분)", example = "O,X", nullable = true)
    private String withImageOrMemo;
}