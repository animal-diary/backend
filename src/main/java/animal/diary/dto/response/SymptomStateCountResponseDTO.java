package animal.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SymptomStateCountResponseDTO {
    private String state;     // 상태명 (예: "NORMAL", "HIGH", "O", "X" 등)
    private int count;        // 해당 상태의 기록 개수
    private List<Integer> dates; // 기록된 날짜들 (일자만)
}