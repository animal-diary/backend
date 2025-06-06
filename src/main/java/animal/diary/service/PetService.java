package animal.diary.service;

import animal.diary.dto.PetRegisterDTO;
import animal.diary.dto.PetRegisterResponseDTO;
import animal.diary.entity.User;
import animal.diary.entity.pet.Disease;
import animal.diary.entity.pet.Pet;
import animal.diary.exception.UserNotFoundException;
import animal.diary.repository.PetRepository;
import animal.diary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetRegisterResponseDTO registerPet(PetRegisterDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 유저")
        );

        Pet pet = PetRegisterDTO.toEntity(dto, user);

        List<Disease> diseaseList = dto.getDisease().stream().map((Disease::fromString)).toList();
        pet.setDiseases(diseaseList);

        petRepository.save(pet);

        return PetRegisterResponseDTO.toDTO(pet);
    }
}
