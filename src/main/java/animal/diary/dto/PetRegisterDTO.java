package animal.diary.dto;

import animal.diary.entity.User;
import animal.diary.entity.pet.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PetRegisterDTO {
    @Schema(description = "CAT, DOG", example = "CAT")
    @NotNull(message = "고양이인지 강아지인지 선택은 필수입니다.")
    private String type;

    @Schema(description = "이름", example = "나비")
    @NotNull(message = "이름 작성은 필수입니다.")
    private String name;

    @Schema(description = "MALE, FEMALE", example = "MALE")
    @NotNull(message = "성별 선택은 필수입니다.")
    private String gender;

    @Schema(description = "중성화 여부 (O: 중성화, X: 비중성화)", example = "O")
    @NotNull(message = "중성화 여부 선택은 필수입니다.")
    private String neutered;

    @Schema(description = "품종", example = "페르시안")
    @NotNull(message = "품종 선택은 필수입니다.")
    private String species;

    @Schema(description = "생년월일", example = "2020-01-01")
    private LocalDate birth;
    @NotNull(message = "건강 상태 선택은 필수입니다.")

    //GOOD, BAD, UNKNOWN;
    @Schema(description = "건강 상태 (GOOD: 건강, BAD: 아픔, UNKNOWN: 알 수 없음)", example = "GOOD")
    private String health;

    @Schema(description = "질병 목록", example = "[\"CARDIOVASCULAR\", \"RESPIRATORY\"]")
    private List<String> disease;

    @Schema(description = "사용자 ID", example = "1")
    private Long userId; // test용

    public static Pet toEntity(PetRegisterDTO dto, User user) {
        return Pet.builder()
                .type(Type.fromString(dto.getType()))
                .name(dto.getName())
                .gender(Gender.fromString(dto.getGender()))
                .neutered(Neutered.fromString(dto.getNeutered()))
                .health(Health.fromString(dto.getHealth()))
                .species(dto.getSpecies())
                .birth(dto.getBirth())
                .user(user)
                .build();
    }
}
