package animal.diary.dto;

import animal.diary.entity.pet.Disease;
import animal.diary.entity.pet.Gender;
import animal.diary.entity.pet.Pet;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class GetMyPetInfoDTO {
    private Long id;
    private String name;
    private String gender;
    private String type;
    private List<String> diseases;

    public static GetMyPetInfoDTO toDTO(Pet pet) {
        return GetMyPetInfoDTO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .gender(Gender.getKoreanName(pet.getGender().name()))
                .type(pet.getType().toString())
                .diseases(
                        pet.getDiseases().stream()
                                .map(Disease::getKoreanName)
                                .sorted() // 가나다순 정렬
                                .limit(4) // 최대 4개 제한
                                .collect(Collectors.toList())
                )
        .build();
    }
}
