package animal.diary.service;

import animal.diary.dto.*;
import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.*;
import animal.diary.exception.EmptyListException;
import animal.diary.exception.InvalidDateException;
import animal.diary.exception.PetNotFoundException;
import animal.diary.repository.*;
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
    private final RRRepository rrRepository;
    private final HeartRateRepository heartRateRepository;
    
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
                .type(pet.getType().toString())
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

    public ResponseDateListDTO getEnergyOrAppetiteByDate(RequestDateDTO dto, String category) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();

        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());
        List<ResponseDateDTO> result = null;


        if (category.equals("energy")) {
            List<Energy> energyList = energyRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);

            if (energyList.isEmpty()) {
                throw new EmptyListException("비어었음");
            }

            result = energyList.stream().map((ResponseDateDTO::energyToDTO)).toList();
        }
        else if (category.equals("appetite")){
            List<Appetite> appetites = appetiteRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);

            if (appetites.isEmpty()) {
                throw new EmptyListException("비어었음");
            }

            result = appetites.stream().map((ResponseDateDTO::appetiteToDTO)).toList();
        }


        return ResponseDateListDTO.builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }


    // 호흡 수
    public RecordResponseDTO recordRRAndHeartRate(RecordNumberDTO dto, String category) {
        Pet pet = getPetOrThrow(dto.getPetId());

        if (category.equals("RR")) {
            RespiratoryRate respiratoryRate = RecordNumberDTO.toRespiratoryRateEntity(dto, pet);
            rrRepository.save(respiratoryRate);
            return RecordResponseDTO.respiratoryRateToDTO(respiratoryRate);

        }
        else if (category.equals("heart-rate")){
            HeartRate heartRate = RecordNumberDTO.toHeartRateEntity(dto, pet);
            heartRateRepository.save(heartRate);
            return RecordResponseDTO.heartRateToDTO(heartRate);
        }
        else {
            return null;
        }

    }

    public ResponseDateListDTO getRRByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        List<RespiratoryRate> respiratoryRateList = rrRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);

        if (respiratoryRateList.isEmpty()) {
            throw new EmptyListException("비어었음");
        }

        List<ResponseDateDTO> result = respiratoryRateList.stream().map((ResponseDateDTO::respiratoryRateTODTO)).toList();

        return ResponseDateListDTO.builder()
                .date(dto.getDate())
                .type(pet.getType().toString())
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
