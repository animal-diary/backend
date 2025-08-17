package animal.diary.dto.record;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DefecationRecordDTO {
    @NotNull(message = "펫 ID는 필수입니다.")
    private Long petId;
    
    @NotNull(message = "대변 상태는 필수입니다.")
    private String state;
}