package animal.diary.dto.request;

import animal.diary.entity.record.state.NumberState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "피부 기록 필터링 요청 DTO")
public class SkinFilterRequestDTO {

    @Schema(description = "피부 상태 (ZERO, ONE, TWO, THREE, FOUR, FIVE)", example = "THREE", nullable = true)
    private NumberState state;

    @Schema(description = "메모/이미지 유무 (O, X 복수 선택 가능, 쉼표로 구분)", example = "O,X", nullable = true)
    private String withImageOrMemo;
}