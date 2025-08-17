package animal.diary.dto;

import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.Sound;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Schema(description = "이상한 소리 기록 DTO")
public class AbnormalSoundRecordDTO {
    @NotNull(message = "펫 ID는 필수입니다.")
    @Schema(description = "펫 ID", example = "1")
    private Long petId;

    @Schema(description = "제목", example = "이상한 소리")
    private String title;

    public static Sound toEntity(AbnormalSoundRecordDTO dto, Pet pet, String imageUrl) {
        return Sound.builder()
                .title(dto.getTitle())
                .pet(pet)
                .imageUrl(imageUrl)
                .build();

    }
}
