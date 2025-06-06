package animal.diary.service;

import animal.diary.dto.RecordNumberDTO;
import animal.diary.dto.RecordResponseDTO;
import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.Weight;
import animal.diary.exception.PetNotFoundException;
import animal.diary.repository.PetRepository;
import animal.diary.repository.WeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final WeightRepository weightRepository;
    private final PetRepository petRepository;

    public RecordResponseDTO record(RecordNumberDTO dto, String category) {
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new PetNotFoundException("펫 못 찾음"));


        Weight weight = RecordNumberDTO.toWeightEntity(dto, pet);

        weightRepository.save(weight);

        return RecordResponseDTO.weightToDTO(weight);
    }
}
