package animal.diary.service;

import animal.diary.code.VitalCategory;
import animal.diary.dto.*;
import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.*;
import animal.diary.exception.EmptyListException;
import animal.diary.exception.ImageSizeLimitException;
import animal.diary.exception.InvalidDateException;
import animal.diary.exception.PetNotFoundException;
import animal.diary.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecordService {
    private final WeightRepository weightRepository;
    private final PetRepository petRepository;
    private final EnergyRepository energyRepository;
    private final AppetiteRepository appetiteRepository;
    private final RRRepository rrRepository;
    private final HeartRateRepository heartRateRepository;
    private final SyncopeRepository syncopeRepository;
    private final UrinaryRepository urinaryRepository;
    private final S3Uploader s3Uploader;
    private final SignificantRepository significantRecordRepository;
    
    // 뭄무게
    public RecordResponseDTO recordWeight(RecordNumberDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        Weight weight = RecordNumberDTO.toWeightEntity(dto, pet);

        log.info("Recording weight for pet ID: {}, weight: {}", pet.getId(), dto.getWeight());
        weightRepository.save(weight);
        log.info("Successfully recorded weight with ID: {}", weight.getId());

        return RecordResponseDTO.weightToDTO(weight);
    }

    public ResponseDateListDTO getWeightsByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();

        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        log.info("Fetching weight records for pet ID: {} on date: {}", pet.getId(), date);
        List<Weight> weights = weightRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
        log.info("Found {} weight records for pet ID: {} on date: {}", weights.size(), pet.getId(), date);

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
            log.info("Recording energy for pet ID: {}, energy level: {}", pet.getId(), dto.getState());
            energyRepository.save(energy);
            log.info("Successfully recorded energy with ID: {}", energy.getId());

            return RecordResponseDTO.energyToDTO(energy);

        }
        else if (category.equals("appetite")) {
            Appetite appetite = RecordNumberDTO.toAppetiteEntity(dto, pet);
            log.info("Recording appetite for pet ID: {}, appetite level: {}", pet.getId(), dto.getState());
            appetiteRepository.save(appetite);
            log.info("Successfully recorded appetite with ID: {}", appetite.getId());

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
            log.info("Fetching energy records for pet ID: {} on date: {}", pet.getId(), date);
            List<Energy> energyList = energyRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
            log.info("Found {} energy records for pet ID: {} on date: {}", energyList.size(), pet.getId(), date);

            if (energyList.isEmpty()) {
                throw new EmptyListException("비어었음");
            }

            result = energyList.stream().map((ResponseDateDTO::energyToDTO)).toList();
        }
        else if (category.equals("appetite")){
            log.info("Fetching appetite records for pet ID: {} on date: {}", pet.getId(), date);
            List<Appetite> appetites = appetiteRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
            log.info("Found {} appetite records for pet ID: {} on date: {}", appetites.size(), pet.getId(), date);

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
    public RecordResponseDTO recordRRAndHeartRate(RecordNumberDTO dto, VitalCategory category) {
        Pet pet = getPetOrThrow(dto.getPetId());

        if (category == VitalCategory.RR) {
            RespiratoryRate respiratoryRate = RecordNumberDTO.toRespiratoryRateEntity(dto, pet);
            log.info("Recording respiratory rate for pet ID: {}, rate: {}", pet.getId(), dto.getCount());
            rrRepository.save(respiratoryRate);
            log.info("Successfully recorded respiratory rate with ID: {}", respiratoryRate.getId());
            return RecordResponseDTO.respiratoryRateToDTO(respiratoryRate);

        }
        else if (category == VitalCategory.HEART_RATE){
            HeartRate heartRate = RecordNumberDTO.toHeartRateEntity(dto, pet);
            log.info("Recording heart rate for pet ID: {}, rate: {}", pet.getId(), dto.getCount());
            heartRateRepository.save(heartRate);
            log.info("Successfully recorded heart rate with ID: {}", heartRate.getId());
            return RecordResponseDTO.heartRateToDTO(heartRate);
        }
        else {
            throw new IllegalArgumentException("Invalid category: " + category);
        }

    }

    public ResponseDateListDTO getRROrHeartRateByDate(RequestDateDTO dto, VitalCategory category) {
        Pet pet = getPetOrThrow(dto.getPetId());
        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        if (category == VitalCategory.RR) {
            log.info("Fetching respiratory rate records for pet ID: {} on date: {}", pet.getId(), dto.getDate());
            List<RespiratoryRate> respiratoryRateList =
                    rrRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
            log.info("Found {} respiratory rate records for pet ID: {} on date: {}", respiratoryRateList.size(), pet.getId(), dto.getDate());

            if (respiratoryRateList.isEmpty()) {
                throw new EmptyListException("호흡 수 기록이 없습니다.");
            }

            List<ResponseDateDTO> result = respiratoryRateList.stream()
                    .map(ResponseDateDTO::respiratoryRateTODTO)
                    .toList();

            return ResponseDateListDTO.builder()
                    .date(dto.getDate())
                    .type(pet.getType().toString())
                    .dateDTOS(result)
                    .build();
        }

        else if (category == VitalCategory.HEART_RATE) {
            log.info("Fetching heart rate records for pet ID: {} on date: {}", pet.getId(), dto.getDate());
            List<HeartRate> heartRateList =
                    heartRateRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
            log.info("Found {} heart rate records for pet ID: {} on date: {}", heartRateList.size(), pet.getId(), dto.getDate());

            if (heartRateList.isEmpty()) {
                throw new EmptyListException("심박수 기록이 없습니다.");
            }

            List<ResponseDateDTO> result = heartRateList.stream()
                    .map(ResponseDateDTO::heartRateToDTO)
                    .toList();

            return ResponseDateListDTO.builder()
                    .date(dto.getDate())
                    .type(pet.getType().toString())
                    .dateDTOS(result)
                    .build();
        }

        else {
            throw new IllegalArgumentException("Invalid category: " + category);
        }
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

    // 기절 상태
    public RecordResponseDTO recordSyncope(RecordNumberDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        Syncope syncope = RecordNumberDTO.toSyncopeEntity(dto, pet);
        log.info("Recording syncope for pet ID: {}, frequency: {}", pet.getId(), dto.getState());
        syncopeRepository.save(syncope);
        log.info("Successfully recorded syncope with ID: {}", syncope.getId());

        return RecordResponseDTO.syncopeToDTO(syncope);
    }

    public ResponseDateListDTO getSyncopeByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();
        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        log.info("Fetching syncope records for pet ID: {} on date: {}", pet.getId(), date);
        List<Syncope> syncopeList = syncopeRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
        log.info("Found {} syncope records for pet ID: {} on date: {}", syncopeList.size(), pet.getId(), date);

        if (syncopeList.isEmpty()) {
            throw new EmptyListException("비어었음");
        }

        List<ResponseDateDTO> result = syncopeList.stream().map(ResponseDateDTO::syncopeToDTO).toList();

        return ResponseDateListDTO.builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }

    // 소변 상태
    public RecordResponseDTO recordUrinary(RecordNumberDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        Urinary urinary = RecordNumberDTO.toUrinaryEntity(dto, pet);
        log.info("Recording urinary for pet ID: {}, frequency: {}", pet.getId(), dto.getUrineAmount());
        urinaryRepository.save(urinary);
        log.info("Successfully recorded urinary with ID: {}", urinary.getId());

        return RecordResponseDTO.urinaryToDTO(urinary);
    }

    public ResponseDateListDTO getUrinaryByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();
        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        log.info("Fetching urinary records for pet ID: {} on date: {}", pet.getId(), date);
        List<Urinary> urinaryList = urinaryRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
        log.info("Found {} urinary records for pet ID: {} on date: {}", urinaryList.size(), pet.getId(), date);

        if (urinaryList.isEmpty()) {
            throw new EmptyListException("비어었음");
        }

        List<ResponseDateDTO> result = urinaryList.stream().map(ResponseDateDTO::urinaryToDTO).toList();

        return ResponseDateListDTO.builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }

    // =============================== 특이 사항 기록 ===============================
    public RecordResponseDTO recordSignificantRecord(SignificantRecordDTO dto, List<MultipartFile> images) throws IOException {
        Pet pet = getPetOrThrow(dto.getPetId());

        // 이미지 10장
        if (images.size() > 10) {
            throw new ImageSizeLimitException("이미지는 최대 10장까지 업로드할 수 있습니다.");
        }

        // 이미지 업로드
        List<String> imageUrls = s3Uploader.uploadMultiple(images, "significant");

        Significant significantRecord = SignificantRecordDTO.toEntity(dto, pet, imageUrls);

        log.info("Recording significant record for pet ID: {}, title: {}", pet.getId(), dto.getTitle());
        significantRecordRepository.save(significantRecord);
        log.info("Successfully recorded significant record with ID: {}", significantRecord.getId());

        return RecordResponseDTO.significantToDTO(significantRecord);
    }


}
