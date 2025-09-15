package animal.diary.service;

import animal.diary.dto.response.FlatStatisticsResponseDTO;
import animal.diary.repository.FlatStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlatStatisticsService {

    private final FlatStatisticsRepository flatStatisticsRepository;

    // ==============================================
    // 복합 필드 엔티티들 (여러 필드)
    // ==============================================

    public FlatStatisticsResponseDTO getUrinaryFlatStatistics(Long petId, int year, int month) {
        log.info("Getting urinary flat statistics for pet: {}, year: {}, month: {}", petId, year, month);
        return flatStatisticsRepository.getUrinaryFlatStatistics(petId, year, month);
    }

    public FlatStatisticsResponseDTO getDefecationFlatStatistics(Long petId, int year, int month) {
        log.info("Getting defecation flat statistics for pet: {}, year: {}, month: {}", petId, year, month);
        return flatStatisticsRepository.getDefecationFlatStatistics(petId, year, month);
    }

    public FlatStatisticsResponseDTO getSkinFlatStatistics(Long petId, int year, int month) {
        log.info("Getting skin flat statistics for pet: {}, year: {}, month: {}", petId, year, month);
        return flatStatisticsRepository.getSkinFlatStatistics(petId, year, month);
    }

    public FlatStatisticsResponseDTO getConvulsionFlatStatistics(Long petId, int year, int month) {
        log.info("Getting convulsion flat statistics for pet: {}, year: {}, month: {}", petId, year, month);
        return flatStatisticsRepository.getConvulsionFlatStatistics(petId, year, month);
    }

    public FlatStatisticsResponseDTO getSnotFlatStatistics(Long petId, int year, int month) {
        log.info("Getting snot flat statistics for pet: {}, year: {}, month: {}", petId, year, month);
        return flatStatisticsRepository.getSnotFlatStatistics(petId, year, month);
    }

    public FlatStatisticsResponseDTO getVomitingFlatStatistics(Long petId, int year, int month) {
        log.info("Getting vomiting flat statistics for pet: {}, year: {}, month: {}", petId, year, month);
        return flatStatisticsRepository.getVomitingFlatStatistics(petId, year, month);
    }

    // ==============================================
    // 단순 상태만 있는 엔티티들 (1개 필드)
    // ==============================================

    public FlatStatisticsResponseDTO getAppetiteFlatStatistics(Long petId, int year, int month) {
        log.info("Getting appetite flat statistics for pet: {}, year: {}, month: {}", petId, year, month);
        return flatStatisticsRepository.getAppetiteFlatStatistics(petId, year, month);
    }

    public FlatStatisticsResponseDTO getEnergyFlatStatistics(Long petId, int year, int month) {
        log.info("Getting energy flat statistics for pet: {}, year: {}, month: {}", petId, year, month);
        return flatStatisticsRepository.getEnergyFlatStatistics(petId, year, month);
    }

    public FlatStatisticsResponseDTO getSyncopeFlatStatistics(Long petId, int year, int month) {
        log.info("Getting syncope flat statistics for pet: {}, year: {}, month: {}", petId, year, month);
        return flatStatisticsRepository.getSyncopeFlatStatistics(petId, year, month);
    }

    public FlatStatisticsResponseDTO getWaterFlatStatistics(Long petId, int year, int month) {
        log.info("Getting water flat statistics for pet: {}, year: {}, month: {}", petId, year, month);
        return flatStatisticsRepository.getWaterFlatStatistics(petId, year, month);
    }
}