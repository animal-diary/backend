package animal.diary.dto;

import animal.diary.entity.pet.Pet;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PetRegisterResponseDTO {
    private Long id;
    private String name;
    private String type;

    public static PetRegisterResponseDTO toDTO(Pet pet) {
        return PetRegisterResponseDTO.builder()
                .id(pet.getId())
                .name(pet.getName())
                .type(pet.getType().toString())
                .build();
    }
}
