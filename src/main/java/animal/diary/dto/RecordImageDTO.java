package animal.diary.dto;

import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.*;
import animal.diary.entity.record.state.BinaryState;
import animal.diary.entity.record.state.NumberState;
import animal.diary.entity.record.state.StoolState;
import animal.diary.exception.InvalidStateException;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
@Data
public class RecordImageDTO {
    
    @NotNull(message = "펫 ID는 필수입니다.")
    private Long petId;
    
    // Sound 관련
    private String title;
    
    // State 관련 (Defecation, Skin, Snot, Vomiting, Convulsion)
    private String state;
    
    // Significant 관련
    private String content;
    
    // 이미지 파일들
    private List<MultipartFile> images;
    
    // 변환 메서드들 - 서비스 레이어에서 구현 예정
}