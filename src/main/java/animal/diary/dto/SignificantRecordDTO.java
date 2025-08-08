package animal.diary.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignificantRecordDTO {
    @NotNull(message = "펫 ID는 필수입니다.")
    private Long petId;
    
    private String title;
    
    @NotNull(message = "내용은 필수입니다.")
    private String content;
}