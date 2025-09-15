package animal.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class FrequencyMonthlyResponseDTO {
    private List<Integer> cautionDates;
    private List<Integer> stableDates;
}
