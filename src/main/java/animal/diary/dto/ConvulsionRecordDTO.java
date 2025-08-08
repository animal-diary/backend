package animal.diary.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Data
public class ConvulsionRecordDTO {
    @NotNull(message = "펫 ID는 필수입니다.")
    private Long petId;
    
    @NotNull(message = "경련 상태는 필수입니다.")
    private String state;
}