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
    private final VomitingRepository vomitingRepository;
    private final WalkingRepository walkingRepository;
    private final WaterRepository waterRepository;
    private final SkinRepository skinRepository;
    private final DefecationRepository defecationRepository;

    // 2.3몸무게 조회
    public ResponseDateListDTO<ResponseDateDTO.WeightResponse> getWeightsByDate(RequestDateDTO dto) {
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

        List<ResponseDateDTO.WeightResponse> result = weights.stream()
                .map(ResponseDateDTO.WeightResponse::weightToDTO)
                .toList();

        return ResponseDateListDTO.<ResponseDateDTO.WeightResponse>builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }

    // 2.4, 2.5기력/식욕 상태 조회
    public ResponseDateListDTO<ResponseDateDTO.StateResponse> getEnergyOrAppetiteByDate(RequestDateDTO dto, String category) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();
        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());
        List<ResponseDateDTO.StateResponse> result = null;

        if (category.equals("energy")) {
            log.info("Fetching energy records for pet ID: {} on date: {}", pet.getId(), date);
            List<Energy> energyList = energyRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
            log.info("Found {} energy records for pet ID: {} on date: {}", energyList.size(), pet.getId(), date);

            if (energyList.isEmpty()) {
                throw new EmptyListException("비어었음");
            }

            result = energyList.stream()
                    .map(ResponseDateDTO.StateResponse::energyToDTO)
                    .toList();
        }
        else if (category.equals("appetite")){
            log.info("Fetching appetite records for pet ID: {} on date: {}", pet.getId(), date);
            List<Appetite> appetites = appetiteRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
            log.info("Found {} appetite records for pet ID: {} on date: {}", appetites.size(), pet.getId(), date);

            if (appetites.isEmpty()) {
                throw new EmptyListException("비어었음");
            }

            result = appetites.stream()
                    .map(ResponseDateDTO.StateResponse::appetiteToDTO)
                    .toList();
        }

        return ResponseDateListDTO.<ResponseDateDTO.StateResponse>builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }

    // 2.7, 호흡수/심박수 조회
    public ResponseDateListDTO<ResponseDateDTO.CountResponse> getRROrHeartRateByDate(RequestDateDTO dto, VitalCategory category) {
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

            List<ResponseDateDTO.CountResponse> result = respiratoryRateList.stream()
                    .map(ResponseDateDTO.CountResponse::respiratoryRateToDTO)
                    .toList();

            return ResponseDateListDTO.<ResponseDateDTO.CountResponse>builder()
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

            List<ResponseDateDTO.CountResponse> result = heartRateList.stream()
                    .map(ResponseDateDTO.CountResponse::heartRateToDTO)
                    .toList();

            return ResponseDateListDTO.<ResponseDateDTO.CountResponse>builder()
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
    public ResponseDateListDTO<ResponseDateDTO.StateResponse> getSyncopeByDate(RequestDateDTO dto) {
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

        List<ResponseDateDTO.StateResponse> result = syncopeList.stream()
                .map(ResponseDateDTO.StateResponse::syncopeToDTO)
                .toList();

        return ResponseDateListDTO.<ResponseDateDTO.StateResponse>builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }

    // 소변 상태 조회
    public ResponseDateListDTO<ResponseDateDTO.UrinaryResponse> getUrinaryByDate(RequestDateDTO dto) {
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

        // 각 record별로 개별 CloudFront URL 생성 (성능 최적화) 컬렉션 전달
        List<ResponseDateDTO.UrinaryResponse> result = urinaryList.stream()
                .map(urinary -> {
                    List<String> imageUrls = urinary.getImageUrls();

                    if (imageUrls == null || imageUrls.isEmpty()) {
                        return ResponseDateDTO.UrinaryResponse.urinaryToDTO(urinary, List.of());
                    }

                    List<String> imageCloudFrontUrls = imageUrls.stream()
                            .map(cloudFrontUrlService::generateSignedUrl)
                            .toList();

                    return ResponseDateDTO.UrinaryResponse.urinaryToDTO(urinary, imageCloudFrontUrls);
                })
                .toList();


        return ResponseDateListDTO.<ResponseDateDTO.UrinaryResponse>builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)   // 여기서 result는 List<ResponseDateDTO.UrinaryResponse>
                .build();
    }

    // 2.6=========================================================== 특이 사항 일별 조회
    public ResponseDateListDTO<ResponseDateDTO.SignificantResponse> getSignificantByDate(RequestDateDTO dto) {
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
        List<ResponseDateDTO.SignificantResponse> result = significantList.stream()
                .map(significant -> {
                    List<String> imageUrls = significant.getImageUrls();

                    String videoUrl = significant.getVideoUrl();

                    if (imageUrls == null || imageUrls.isEmpty()) {
                        return ResponseDateDTO.SignificantResponse.significantToDTO(significant, List.of(), videoUrl);
                    }

                    List<String> imageCloudFrontUrls = imageUrls.stream()
                            .map(cloudFrontUrlService::generateSignedUrl)
                            .toList();

                    return ResponseDateDTO.SignificantResponse.significantToDTO(significant, imageCloudFrontUrls, videoUrl);
                })
                .toList();

        return ResponseDateListDTO.<ResponseDateDTO.SignificantResponse>builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }

    // 2.10=========================================================== 경련 상태 일별 조회
    public ResponseDateListDTO<ResponseDateDTO.ConvulsionResponse> getConvulsionByDate(RequestDateDTO dto) {
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
        List<ResponseDateDTO.ConvulsionResponse> result = convulsionList.stream().map(convulsion -> {
            if (convulsion.getVideoUrl() == null || convulsion.getVideoUrl().isEmpty()) {
                return ResponseDateDTO.ConvulsionResponse.convulsionToDTO(convulsion, null);
            }
            String videoUrl = convulsion.getVideoUrl();

            String imageCloudFrontUrl = cloudFrontUrlService.generateSignedUrl(videoUrl);
            
            return ResponseDateDTO.ConvulsionResponse.convulsionToDTO(convulsion, imageCloudFrontUrl);
        }).toList();

        return ResponseDateListDTO.<ResponseDateDTO.ConvulsionResponse>builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }

    // 2.8=========================================================== 이상 소리 일별 조회
    public ResponseDateListDTO<ResponseDateDTO.SoundResponse> getSoundByDate(RequestDateDTO dto) {
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
        List<ResponseDateDTO.SoundResponse> result = soundList.stream().map(sound -> {
            if (sound.getImageUrl() == null || sound.getImageUrl().isEmpty()) {
                return ResponseDateDTO.SoundResponse.soundToDTO(sound, null);
            }
            String imageUrl = sound.getImageUrl();

            String imageCloudFrontUrl = cloudFrontUrlService.generateSignedUrl(imageUrl);

            return ResponseDateDTO.SoundResponse.soundToDTO(sound, imageCloudFrontUrl);
        }).toList();

        return ResponseDateListDTO.<ResponseDateDTO.SoundResponse>builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }

    // 2.9============================================================== 콧물 일별 조회
    public ResponseDateListDTO<ResponseDateDTO.SnotResponse> getSnotByDate(RequestDateDTO dto) {
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

        List<ResponseDateDTO.SnotResponse> result = snotList.stream().map(
                        snot -> {
                            List<String> imageUrls = snot.getImageUrls();
                            if (imageUrls == null || imageUrls.isEmpty()) {
                                return ResponseDateDTO.SnotResponse.snotToDTO(snot, List.of());
                            }

                            // 스트림으로 변환하여 성능 향상
                            List<String> imageCloudFrontUrls = imageUrls.stream()
                                    .map(cloudFrontUrlService::generateSignedUrl)
                                    .toList();

                            return ResponseDateDTO.SnotResponse.snotToDTO(snot, imageCloudFrontUrls);
                        }
                ).toList();

        return ResponseDateListDTO.<ResponseDateDTO.SnotResponse>builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();

    }

    // 2.11============================================================== 구토 일별 조회
    public ResponseDateListDTO<ResponseDateDTO.VomitingResponse> getVomitingByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();
        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        log.info("Fetching vomiting records for pet ID: {} on date: {}", pet.getId(), date);
        List<Vomiting> vomitingList = vomitingRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
        log.info("Found {} vomiting records for pet ID: {} on date: {}", vomitingList.size(), pet.getId(), date);

        if (vomitingList.isEmpty()) {
            throw new EmptyListException("비어있음");
        }

        List<ResponseDateDTO.VomitingResponse> result = vomitingList.stream().map(
                vomiting -> {
                    List<String> imageUrls = vomiting.getImageUrls();
                    if (imageUrls == null || imageUrls.isEmpty()) {
                        return ResponseDateDTO.VomitingResponse.vomitingToDTO(vomiting, List.of());
                    }

                    // 스트림으로 변환하여 성능 향상
                    List<String> imageCloudFrontUrls = imageUrls.stream()
                            .map(cloudFrontUrlService::generateSignedUrl)
                            .toList();

                    return ResponseDateDTO.VomitingResponse.vomitingToDTO(vomiting, imageCloudFrontUrls);
                }
        ).toList();

        return ResponseDateListDTO.<ResponseDateDTO.VomitingResponse>builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }

    // 2.16 ============================================================== 걷는 모습 일별 조회
    public ResponseDateListDTO<ResponseDateDTO.WalkingResponse> getWalkingByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();
        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        log.info("Fetching walking records for pet ID: {} on date: {}", pet.getId(), date);
        List<Walking> walkingList = walkingRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
        log.info("Found {} walking records for pet ID: {} on date: {}", walkingList.size(), pet.getId(), date);

        if (walkingList.isEmpty()) {
            throw new EmptyListException("비어있음");
        }

        List<ResponseDateDTO.WalkingResponse> result = walkingList.stream().map(
                walking -> {
                    String videoUrl = walking.getImageUrl();
                    if (videoUrl == null || videoUrl.isEmpty()) {
                        return ResponseDateDTO.WalkingResponse.walkingToDTO(walking, null);
                    }

                    String videoCloudFrontUrl = cloudFrontUrlService.generateSignedUrl(videoUrl);

                    return ResponseDateDTO.WalkingResponse.walkingToDTO(walking, videoCloudFrontUrl);
                }
        ).toList();

        return ResponseDateListDTO.<ResponseDateDTO.WalkingResponse>builder()
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


    public ResponseDateListDTO<ResponseDateDTO.WaterResponse> getWaterByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();
        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        log.info("Fetching water records for pet ID: {} on date: {}", pet.getId(), date);
        List<Water> waterList = waterRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
        log.info("Found {} water records for pet ID: {} on date: {}", waterList.size(), pet.getId(), date);

        if (waterList.isEmpty()) {
            throw new EmptyListException("비어었음");
        }

        List<ResponseDateDTO.WaterResponse> result = waterList.stream()
                .map(ResponseDateDTO.WaterResponse::waterToDTO)
                .toList();

        return ResponseDateListDTO.<ResponseDateDTO.WaterResponse>builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }

    public ResponseDateListDTO<ResponseDateDTO.SkinResponse> getSkinByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();
        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        log.info("Fetching skin records for pet ID: {} on date: {}", pet.getId(), date);
        List<Skin> skinList = skinRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
        log.info("Found {} skin records for pet ID: {} on date: {}", skinList.size(), pet.getId(), date);

        if (skinList.isEmpty()) {
            throw new EmptyListException("비어었음");
        }

        List<ResponseDateDTO.SkinResponse> result = skinList.stream().map(
                skin -> {
                    List<String> imageUrls = skin.getImageUrls();
                    if (imageUrls == null || imageUrls.isEmpty()) {
                        return ResponseDateDTO.SkinResponse.skinToDTO(skin, List.of());
                    }

                    // 스트림으로 변환하여 성능 향상
                    List<String> imageCloudFrontUrls = imageUrls.stream()
                            .map(cloudFrontUrlService::generateSignedUrl)
                            .toList();

                    return ResponseDateDTO.SkinResponse.skinToDTO(skin, imageCloudFrontUrls);
                }
        ).toList();

        return ResponseDateListDTO.<ResponseDateDTO.SkinResponse>builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }

    // 2.18============================================================== 배변 일별 조회
    public ResponseDateListDTO<ResponseDateDTO.DefecationResponse> getDefecationByDate(RequestDateDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        LocalDate date = dto.getDate();
        validateDate(dto.getDate());

        LocalDateTime[] range = getStartAndEndOfDay(dto.getDate());

        log.info("Fetching defecation records for pet ID: {} on date: {}", pet.getId(), date);
        List<Defecation> defecationList = defecationRepository.findAllByPetIdAndCreatedAtBetween(pet.getId(), range[0], range[1]);
        log.info("Found {} defecation records for pet ID: {} on date: {}", defecationList.size(), pet.getId(), date);

        if (defecationList.isEmpty()) {
            throw new EmptyListException("비어있음");
        }

        List<ResponseDateDTO.DefecationResponse> result = defecationList.stream().map(
                defecation -> {
                    List<String> imageUrls = defecation.getImageUrls();
                    if (imageUrls == null || imageUrls.isEmpty()) {
                        return ResponseDateDTO.DefecationResponse.defecationToDTO(defecation, List.of());
                    }

                    // 스트림으로 변환하여 성능 향상
                    List<String> imageCloudFrontUrls = imageUrls.stream()
                            .map(cloudFrontUrlService::generateSignedUrl)
                            .toList();

                    return ResponseDateDTO.DefecationResponse.defecationToDTO(defecation, imageCloudFrontUrls);
                }
        ).toList();

        return ResponseDateListDTO.<ResponseDateDTO.DefecationResponse>builder()
                .date(date)
                .type(pet.getType().toString())
                .dateDTOS(result)
                .build();
    }
}