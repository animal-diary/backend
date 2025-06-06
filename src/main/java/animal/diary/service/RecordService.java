package animal.diary.service;

import animal.diary.dto.*;
import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.Weight;
import animal.diary.exception.EmptyListException;
import animal.diary.exception.InvalidDateException;
import animal.diary.exception.PetNotFoundException;
import animal.diary.repository.PetRepository;
import animal.diary.repository.WeightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public ResponseWeightDateDTO getWeightsByDate(RequestWeightDateDTO dto) {
        Pet pet = petRepository.findById(dto.getPetId())
                .orElseThrow(() -> new PetNotFoundException("펫 못 찾음"));
        LocalDate date = dto.getDate();

        if (date.isAfter(LocalDate.now())) {
            throw new InvalidDateException("미래 선택 ㄴㄴ");
        }
        LocalDateTime start = date.atStartOfDay();

        LocalDateTime end = date.atTime(LocalTime.MAX);

        List<Weight> weights = weightRepository.findAllByPetIdAndCreatedAtBetween(dto.getPetId(), start, end);

        if (weights.isEmpty()) {
            throw new EmptyListException("비어었음");
        }
        List<ResponseWeightDTO> result = weights.stream().map((ResponseWeightDTO::weightToDTO)).toList();

        return ResponseWeightDateDTO.builder()
                .date(date)
                .weightDTOS(result)
                .build();
    }
}
