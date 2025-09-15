package animal.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlatStatisticsResponseDTO {
    // 각 필드별 독립적인 통계 데이터
    private Map<String, List<SymptomStateCountResponseDTO>> fieldStatistics;
}