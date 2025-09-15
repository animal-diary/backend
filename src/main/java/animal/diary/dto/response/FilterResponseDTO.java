package animal.diary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "필터링 결과 응답 DTO")
public class FilterResponseDTO {

    @Schema(description = "필터링된 조건을 만족하는 기록이 있는 날짜들", example = "[5, 12, 18, 25]")
    private List<Integer> dates;

    @Schema(description = "총 개수", example = "4")
    private int count;
}