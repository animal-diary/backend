package animal.diary.service;

import animal.diary.dto.GetMyPetInfoDTO;
import animal.diary.dto.PetRegisterDTO;
import animal.diary.dto.PetRegisterResponseDTO;
import animal.diary.entity.User;
import animal.diary.entity.pet.Disease;
import animal.diary.entity.pet.Pet;
import animal.diary.exception.UserNotFoundException;
import animal.diary.repository.PetRepository;
import animal.diary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

        log.info("Registering new pet: name={}, type={}, userId={}", pet.getName(), pet.getType(), user.getId());
        petRepository.save(pet);
        log.info("Successfully registered pet with ID: {}", pet.getId());

        return PetRegisterResponseDTO.toDTO(pet);
    }

    public List<GetMyPetInfoDTO> getMyPetInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 유저")
        );

        log.info("Fetching pet list for user ID: {}", userId);
        List<Pet> petList = petRepository.findByUserId(user.getId());
        log.info("Found {} pets for user ID: {}", petList.size(), userId);

        return petList.stream().map((GetMyPetInfoDTO::toDTO)).collect(Collectors.toList());
    }

    // 펫 정보 가져오기
    public Pet getPetById(Long petId) {
        return petRepository.findById(petId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 펫")
        );
    }

}
