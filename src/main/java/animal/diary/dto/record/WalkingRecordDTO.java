package animal.diary.dto.record;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WalkingRecordDTO {
    @NotNull(message = "펫 ID는 필수입니다.")
    private Long petId;
    
    @NotNull(message = "제목은 필수입니다.")
    private String title;
}