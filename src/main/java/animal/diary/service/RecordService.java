package animal.diary.service;

import animal.diary.code.ErrorCode;
import animal.diary.code.VitalCategory;
import animal.diary.dto.*;
import animal.diary.dto.record.ConvulsionRecordDTO;
import animal.diary.dto.record.RecordWithOutImageDTO;
import animal.diary.dto.record.SignificantRecordDTO;
import animal.diary.dto.record.SnotRecordDTO;
import animal.diary.entity.pet.Pet;
import animal.diary.entity.record.*;
import animal.diary.exception.ImageSizeLimitException;
import animal.diary.exception.PetNotFoundException;
import animal.diary.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final ConvulsionRepository convulsionRecordRepository;
    private final SoundRepository soundRepository;
    private final SnotRepository snotRepository;
    
    // 몸무게 기록
    public RecordResponseDTO recordWeight(RecordWithOutImageDTO.WeightRecordDTO dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        Weight weight = RecordWithOutImageDTO.WeightRecordDTO.toWeightEntity(dto, pet);

        log.info("Recording weight for pet ID: {}, weight: {}", pet.getId(), dto.getWeight());
        weightRepository.save(weight);
        log.info("Successfully recorded weight with ID: {}", weight.getId());

        return RecordResponseDTO.weightToDTO(weight);
    }

    // ========================================================= 기력/식욕 상태 기록
    public RecordResponseDTO recordEnergyAndAppetite(RecordWithOutImageDTO.EnergyAndAppetiteRecord dto, String category) {
        Pet pet = getPetOrThrow(dto.getPetId());

        if (category.equals("energy")) {
            Energy energy = RecordWithOutImageDTO.EnergyAndAppetiteRecord.toEnergyEntity(dto, pet);
            log.info("Recording energy for pet ID: {}, energy level: {}", pet.getId(), dto.getState());
            energyRepository.save(energy);
            log.info("Successfully recorded energy with ID: {}", energy.getId());

            return RecordResponseDTO.energyToDTO(energy);

        }
        else if (category.equals("appetite")) {
            Appetite appetite = RecordWithOutImageDTO.EnergyAndAppetiteRecord.toAppetiteEntity(dto, pet);
            log.info("Recording appetite for pet ID: {}, appetite level: {}", pet.getId(), dto.getState());
            appetiteRepository.save(appetite);
            log.info("Successfully recorded appetite with ID: {}", appetite.getId());

            return RecordResponseDTO.appetiteToDTO(appetite);
        }

        else {return null;}
    }

    // ==================================================== 호흡수/심박수 기록
    public RecordResponseDTO recordRRAndHeartRate(RecordWithOutImageDTO.RespiratoryRateAndHeartRateRecord dto, VitalCategory category) {
        Pet pet = getPetOrThrow(dto.getPetId());

        if (category == VitalCategory.RR) {
            RespiratoryRate respiratoryRate = RecordWithOutImageDTO.RespiratoryRateAndHeartRateRecord.toRespiratoryRateEntity(dto, pet);
            log.info("Recording respiratory rate for pet ID: {}, rate: {}", pet.getId(), dto.getCount());
            rrRepository.save(respiratoryRate);
            log.info("Successfully recorded respiratory rate with ID: {}", respiratoryRate.getId());
            return RecordResponseDTO.respiratoryRateToDTO(respiratoryRate);

        }
        else if (category == VitalCategory.HEART_RATE){
            HeartRate heartRate = RecordWithOutImageDTO.RespiratoryRateAndHeartRateRecord.toHeartRateEntity(dto, pet);
            log.info("Recording heart rate for pet ID: {}, rate: {}", pet.getId(), dto.getCount());
            heartRateRepository.save(heartRate);
            log.info("Successfully recorded heart rate with ID: {}", heartRate.getId());
            return RecordResponseDTO.heartRateToDTO(heartRate);
        }
        else {
            throw new IllegalArgumentException("Invalid category: " + category);
        }
    }

    // ============================================== 기절 상태 기록
    public RecordResponseDTO recordSyncope(RecordWithOutImageDTO.SyncopeRecord dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        Syncope syncope = RecordWithOutImageDTO.SyncopeRecord.toSyncopeEntity(dto, pet);
        log.info("Recording syncope for pet ID: {}, frequency: {}", pet.getId(), dto.getBinaryState());
        syncopeRepository.save(syncope);
        log.info("Successfully recorded syncope with ID: {}", syncope.getId());

        return RecordResponseDTO.syncopeToDTO(syncope);
    }

    // ======================================================= 소변 상태 기록
    public RecordResponseDTO recordUrinary(RecordWithOutImageDTO.UrinaryRecord dto) {
        Pet pet = getPetOrThrow(dto.getPetId());

        Urinary urinary = RecordWithOutImageDTO.UrinaryRecord.toUrinaryEntity(dto, pet);
        log.info("Recording urinary for pet ID: {}, frequency: {}", pet.getId(), dto.getUrineAmount());
        urinaryRepository.save(urinary);
        log.info("Successfully recorded urinary with ID: {}", urinary.getId());

        return RecordResponseDTO.urinaryToDTO(urinary);
    }

    // ============================================== 특이사항 기록
    public RecordResponseDTO recordSignificantRecord(SignificantRecordDTO dto, List<MultipartFile> images) {
        Pet pet = getPetOrThrow(dto.getPetId());

        // 이미지 10장 제한
        if (images.size() > 10) {
            throw new ImageSizeLimitException(ErrorCode.IMAGE_SIZE_LIMIT_10);
        }

        // 이미지 업로드
        List<String> imageUrls = s3Uploader.uploadMultiple(images, "significant");

        Significant significantRecord = SignificantRecordDTO.toEntity(dto, pet, imageUrls);

        log.info("Recording significant record for pet ID: {}, title: {}", pet.getId(), dto.getTitle());
        significantRecordRepository.save(significantRecord);
        log.info("Successfully recorded significant record with ID: {}", significantRecord.getId());

        return RecordResponseDTO.significantToDTO(significantRecord);
    }

    // ========================================================= 경련 기록
    public RecordResponseDTO recordConvulsionRecord(ConvulsionRecordDTO dto, MultipartFile image) {
        Pet pet = getPetOrThrow(dto.getPetId());

        // 단일 이미지 업로드
        String imageUrl = "";
        if (image != null && !image.isEmpty()) {
            imageUrl = s3Uploader.upload(image, "convulsion");
        }

        Convulsion convulsionRecord = ConvulsionRecordDTO.toEntity(dto, pet, imageUrl);

        log.info("Recording convulsion record for pet ID: {}, title: {}", pet.getId(), dto.getState());
        convulsionRecordRepository.save(convulsionRecord);
        log.info("Successfully recorded convulsion record with ID: {}", convulsionRecord.getId());

        return RecordResponseDTO.convulsionToDTO(convulsionRecord);
    }

    // ======================================================== 이상 소리 기록
    public RecordResponseDTO recordSound(AbnormalSoundRecordDTO dto, MultipartFile image) {
        Pet pet = getPetOrThrow(dto.getPetId());

        // 이미지 업로드
        String imageUrl = "";
        if (image != null && !image.isEmpty()) {
            imageUrl = s3Uploader.upload(image, "sound");
        }

        Sound sound = AbnormalSoundRecordDTO.toEntity(dto, pet, imageUrl);
        log.info("Recording sound for pet ID: {}, title: {}", pet.getId(), dto.getTitle());
        soundRepository.save(sound);
        log.info("Successfully recorded sound with ID: {}", sound.getId());

        return RecordResponseDTO.soundToDTO(sound);
    }

    // 공통 유틸리티 메서드
    private Pet getPetOrThrow(Long petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("펫 못 찾음"));
    }

    public RecordResponseDTO recordSnot(SnotRecordDTO dto, List<MultipartFile> images) {
        Pet pet = getPetOrThrow(dto.getPetId());

        // 이미지 10장 제한
        if (images.size() > 10) {
            throw new ImageSizeLimitException(ErrorCode.IMAGE_SIZE_LIMIT_10);
        }

        // 이미지 업로드
        List<String> imageUrls = s3Uploader.uploadMultiple(images, "snot");

        Snot snotRecord = SnotRecordDTO.toEntity(dto, pet, imageUrls);

        log.info("Recording snot for pet ID: {}, title: {}", pet.getId(), dto.getState());
        snotRepository.save(snotRecord);
        log.info("Successfully recorded snot with ID: {}", snotRecord.getId());

        return RecordResponseDTO.snotToDTO(snotRecord);
    }
}