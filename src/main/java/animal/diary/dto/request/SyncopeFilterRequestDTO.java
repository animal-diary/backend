package animal.diary.dto.request;

import animal.diary.entity.record.state.BinaryState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "실신 기록 필터링 요청 DTO")
public class SyncopeFilterRequestDTO {

    @Schema(description = "실신 상태 (O, X)", example = "O", nullable = true)
    private BinaryState state;
}