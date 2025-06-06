package animal.diary.service;

import animal.diary.dto.*;
import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.Appetite;
import animal.diary.entity.record.Energy;
import animal.diary.entity.record.Weight;
import animal.diary.exception.EmptyListException;
import animal.diary.exception.InvalidDateException;
import animal.diary.exception.PetNotFoundException;
import animal.diary.repository.AppetiteRepository;
import animal.diary.repository.EnergyRepository;
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
    private final EnergyRepository energyRepository;
    private final AppetiteRepository appetiteRepository;

    
    // 뭄무게
    public RecordResponseDTO recordWeight(RecordNumberDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        Weight weight = RecordNumberDTO.toWeightEntity(dto, pet);

        weightRepository.save(weight);

        return RecordResponseDTO.weightToDTO(weight);
    }

    public ResponseDateListDTO getWeightsByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();

        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        List<Weight> weights = weightRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);

        if (weights.isEmpty()) {
            throw new EmptyListException("비어었음");
        }

        List<ResponseDateDTO> result = weights.stream().map((ResponseDateDTO::weightToDTO)).toList();

        return ResponseDateListDTO.builder()
                .date(date)
                .dateDTOS(result)
                .build();
    }

    // 기력 상태
    public RecordResponseDTO recordEnergyAndAppetite(RecordNumberDTO dto, String category) {
        Pet pet = getPetOrThrow(dto.getPetId());

        if (category.equals("energy")) {
            Energy energy = RecordNumberDTO.toEnergyEntity(dto, pet);
            energyRepository.save(energy);

            return RecordResponseDTO.energyToDTO(energy);

        }
        else if (category.equals("appetite")) {
            Appetite appetite = RecordNumberDTO.toAppetiteEntity(dto, pet);
            appetiteRepository.save(appetite);

            return RecordResponseDTO.appetiteToDTO(appetite);
        }

        else {return null;}
    }

    public ResponseDateListDTO getEnergyByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();

        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        List<Energy> energyList = energyRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);

        if (energyList.isEmpty()) {
            throw new EmptyListException("비어었음");
        }

        List<ResponseDateDTO> result = energyList.stream().map((ResponseDateDTO::energyToDTO)).toList();

        return ResponseDateListDTO.builder()
                .date(date)
                .dateDTOS(result)
                .build();
    }

    private Pet getPetOrThrow(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("펫 못 찾음"));
    }

    private void validateDate(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw new InvalidDateException("미래 선택 ㄴㄴ");
        }
    }

    private LocalDateTime[] getStartAndEndOfDay(LocalDate date) {
        return new LocalDateTime[]{date.atStartOfDay(), date.atTime(LocalTime.MAX)};
    }
}
