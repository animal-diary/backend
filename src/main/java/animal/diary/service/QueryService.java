package animal.diary.service;

import animal.diary.code.VitalCategory;
import animal.diary.dto.RequestDateDTO;
import animal.diary.dto.ResponseDateDTO;
import animal.diary.dto.ResponseDateListDTO;
import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.*;
import animal.diary.exception.EmptyListException;
import animal.diary.exception.InvalidDateException;
import animal.diary.exception.PetNotFoundException;
import animal.diary.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryService {
    
    private final WeightRepository weightRepository;
    private final PetRepository petRepository;
    private final EnergyRepository energyRepository;
    private final AppetiteRepository appetiteRepository;
    private final RRRepository rrRepository;
    private final HeartRateRepository heartRateRepository;
    private final SyncopeRepository syncopeRepository;
    private final UrinaryRepository urinaryRepository;
    private final SignificantRepository significantRepository;
    private final ConvulsionRepository convulsionRepository;
    private final CloudFrontUrlService cloudFrontUrlService;
    private final SoundRepository soundRepository;
    private final SnotRepository snotRepository;

    // 몸무게 조회
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

    // 기력/식욕 상태 조회
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

    // 호흡수/심박수 조회
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

    // 기절 상태 조회
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

    // 소변 상태 조회
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

    // =========================================================== 특이 사항 일별 조회
    public ResponseDateListDTO getSignificantByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();
        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        log.info("Fetching significant records for pet ID: {} on date: {}", pet.getId(), date);
        List<Significant> significantList = significantRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
        log.info("Found {} significant records for pet ID: {} on date: {}", significantList.size(), pet.getId(), date);

        if (significantList.isEmpty()) {
            throw new EmptyListException("비어었음");
        }

        // 각 record별로 개별 CloudFront URL 생성 (성능 최적화)
        List<ResponseDateDTO> result = significantList.stream().map(significant -> {
            List<String> imageUrls = significant.getImageUrls();
            
            if (imageUrls == null || imageUrls.isEmpty()) {
                return ResponseDateDTO.significantToDTO(significant, List.of());
            }
            
            // 스트림으로 변환하여 성능 향상
            List<String> imageCloudFrontUrls = imageUrls.stream()
                    .map(cloudFrontUrlService::generateSignedUrl)
                    .toList();
            
            return ResponseDateDTO.significantToDTO(significant, imageCloudFrontUrls);
        }).toList();

        return ResponseDateListDTO.builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }

    // =========================================================== 경련 상태 일별 조회
    public ResponseDateListDTO getConvulsionByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();
        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        log.info("Fetching convulsion records for pet ID: {} on date: {}", pet.getId(), date);
        List<Convulsion> convulsionList = convulsionRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
        log.info("Found {} convulsion records for pet ID: {} on date: {}", convulsionList.size(), pet.getId(), date);

        if (convulsionList.isEmpty()) {
            throw new EmptyListException("비어었음");
        }

        // 각 record별로 개별 CloudFront URL 생성 (성능 최적화)
        List<ResponseDateDTO> result = convulsionList.stream().map(convulsion -> {
            if (convulsion.getImageUrl() == null || convulsion.getImageUrl().isEmpty()) {
                return ResponseDateDTO.convulsionToDTO(convulsion, null);
            }
            String imageUrl = convulsion.getImageUrl();

            String imageCloudFrontUrl = cloudFrontUrlService.generateSignedUrl(imageUrl);
            
            return ResponseDateDTO.convulsionToDTO(convulsion, imageCloudFrontUrl);
        }).toList();

        return ResponseDateListDTO.builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }

    // =========================================================== 이상 소리 일별 조회
    public ResponseDateListDTO getSoundByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();
        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        log.info("Fetching sound records for pet ID: {} on date: {}", pet.getId(), date);
        List<Sound> soundList = soundRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
        log.info("Found {} sound records for pet ID: {} on date: {}", soundList.size(), pet.getId(), date);

        if (soundList.isEmpty()) {
            throw new EmptyListException("비어었음");
        }

        // 각 record별로 개별 CloudFront URL 생성 (성능 최적화)
        List<ResponseDateDTO> result = soundList.stream().map(sound -> {
            if (sound.getImageUrl() == null || sound.getImageUrl().isEmpty()) {
                return ResponseDateDTO.soundToDTO(sound, null);
            }
            String imageUrl = sound.getImageUrl();

            String imageCloudFrontUrl = cloudFrontUrlService.generateSignedUrl(imageUrl);

            return ResponseDateDTO.soundToDTO(sound, imageCloudFrontUrl);
        }).toList();

        return ResponseDateListDTO.builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }

    // 공통 유틸리티 메서드들
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

    // ============================================================== 콧물 일별 조회
    public ResponseDateListDTO getSnotByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();
        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        log.info("Fetching snot records for pet ID: {} on date: {}", pet.getId(), date);
        List<Snot> snotList = snotRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
        log.info("Found {} snot records for pet ID: {} on date: {}", snotList.size(), pet.getId(), date);

        if (snotList.isEmpty()) {
            throw new EmptyListException("비어있음");
        }

        List<ResponseDateDTO> result = snotList.stream().map(
            snot -> {
                List<String> imageUrls = snot.getImageUrls();
                if (imageUrls == null || imageUrls.isEmpty()) {
                    return ResponseDateDTO.snotToDTO(snot, List.of());
                }

                // 스트림으로 변환하여 성능 향상
                List<String> imageCloudFrontUrls = imageUrls.stream()
                        .map(cloudFrontUrlService::generateSignedUrl)
                        .toList();

                return ResponseDateDTO.snotToDTO(snot, imageCloudFrontUrls);
            }
        ).toList();

        return ResponseDateListDTO.builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();

    }
}