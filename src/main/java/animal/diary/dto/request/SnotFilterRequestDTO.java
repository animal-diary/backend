package animal.diary.dto.request;

import animal.diary.entity.record.state.SnotState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "콧물 기록 필터링 요청 DTO")
public class SnotFilterRequestDTO {

    @Schema(description = "콧물 상태 (CLEAR, THICK, BLOODY, ETC)", example = "CLEAR", nullable = true)
    private SnotState state;

    @Schema(description = "메모/이미지 유무 (O, X 복수 선택 가능, 쉼표로 구분)", example = "O,X", nullable = true)
    private String withImageOrMemo;
}