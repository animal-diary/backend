package animal.diary.dto.record;

import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.Snot;
import animal.diary.entity.record.Vomiting;
import animal.diary.entity.record.state.BinaryState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@Schema(description = "펫의 구토 기록 DTO")
public class VomitingRecordDTO {
    @Schema(description = "펫 ID", example = "1")
    @NotNull(message = "펫 ID는 필수입니다.")
    private Long petId;

    @Schema(description = "구토 상태 (O, X)", example = "O")
    @NotNull(message = "구토 상태는 필수입니다.")
    private String state;

    public static Vomiting toEntity(VomitingRecordDTO dto, Pet pet, List<String> imageUrls) {
        return Vomiting.builder()
                .pet(pet)
                .state(BinaryState.fromString(dto.getState()))
                .imageUrls(imageUrls)
                .build();
    }
}