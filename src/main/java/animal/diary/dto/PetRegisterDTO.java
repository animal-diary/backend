package animal.diary.dto;

import animal.diary.entity.User;
import animal.diary.entity.pet.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Data
public class PetRegisterDTO {
    @NotNull(message = "고양이인지 강아지인지 선택은 필수입니다.")
    private String type;
    @NotNull(message = "이름 작성은 필수입니다.")
    private String name;
    @NotNull(message = "성별 선택은 필수입니다.")
    private String gender;
    @NotNull(message = "중성화 여부 선택은 필수입니다.")
    private String neutered;
    @NotNull(message = "품종 선택은 필수입니다.")
    private String species;
    private LocalDate birth;
    @NotNull(message = "건강 상태 선택은 필수입니다.")
    private String health;
    private List<String> disease;
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
