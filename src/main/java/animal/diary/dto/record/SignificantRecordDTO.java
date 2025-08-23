package animal.diary.dto.record;

import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.Significant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@Schema(description = "특이 사항 기록 DTO")
public class SignificantRecordDTO {
    @NotNull(message = "펫 ID는 필수입니다.")
    @Schema(description = "펫 ID", example = "1")
    private Long petId;

    @Schema(description = "제목", example = "첫 번째 산책")
    @Size(max = 200, message = "제목은 200자 이내여야 합니다.")
    private String title;

    @Schema(description = "내용", example = "오늘은 첫 번째 산책을 나갔다. 다리를 저는 것 같다.")
    @Size(max = 200, message = "내용은 200자 이내여야 합니다.")
    private String content;

    @AssertTrue(message = "제목 또는 내용 중 하나는 반드시 입력해야 합니다.")
    private boolean isTitleOrContentPresent() {
        return (title != null && !title.trim().isEmpty()) || 
               (content != null && !content.trim().isEmpty());
    }

    public static Significant toEntity(SignificantRecordDTO dto, Pet pet, List<String> imageUrls, String videoUrl) {
        return Significant.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .pet(pet)
                .imageUrls(imageUrls)
                .videoUrl(videoUrl)
                .build();
    }
}