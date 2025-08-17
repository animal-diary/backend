package animal.diary.dto.record;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VomitingRecordDTO {
    @NotNull(message = "펫 ID는 필수입니다.")
    private Long petId;
    
    @NotNull(message = "구토 상태는 필수입니다.")
    private String state;
}