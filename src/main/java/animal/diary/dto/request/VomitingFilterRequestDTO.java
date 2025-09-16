package animal.diary.dto.request;

import animal.diary.entity.record.state.BinaryState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "구토 기록 필터링 요청 DTO")
public class VomitingFilterRequestDTO {

    @Schema(description = "구토 상태 (O, X)", example = "O", nullable = true)
    private BinaryState state;

    @Schema(description = "이미지 유무 (O, X 복수 선택 가능, 쉼표로 구분)", example = "O,X", nullable = true)
    private String withImage;
}