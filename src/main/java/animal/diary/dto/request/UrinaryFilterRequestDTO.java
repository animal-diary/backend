package animal.diary.dto.request;

import animal.diary.entity.record.state.BinaryState;
import animal.diary.entity.record.state.LevelState;
import animal.diary.entity.record.state.UrineState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "소변 기록 필터링 요청 DTO")
public class UrinaryFilterRequestDTO {

    @Schema(description = "소변량 (NONE, LOW, NORMAL, HIGH)", example = "NORMAL", nullable = true)
    private LevelState output;

    @Schema(description = "소변 상태 (BLOODY, LIGHT, DARK, NORMAL, ETC)", example = "BLOODY", nullable = true)
    private UrineState state;

    @Schema(description = "악취 유무 (O, X)", example = "O", nullable = true)
    private BinaryState binaryState;

    @Schema(description = "메모/이미지 유무 (O, X 복수 선택 가능, 쉼표로 구분)", example = "O,X", nullable = true)
    private String withImageOrMemo;
}