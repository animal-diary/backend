package animal.diary.service;

import animal.diary.dto.request.ConvulsionFilterRequestDTO;
import animal.diary.dto.request.UrinaryFilterRequestDTO;
import animal.diary.dto.request.DefecationFilterRequestDTO;
import animal.diary.dto.request.SkinFilterRequestDTO;
import animal.diary.dto.request.SnotFilterRequestDTO;
import animal.diary.dto.request.VomitingFilterRequestDTO;
import animal.diary.dto.request.WaterFilterRequestDTO;
import animal.diary.dto.request.AppetiteFilterRequestDTO;
import animal.diary.dto.request.EnergyFilterRequestDTO;
import animal.diary.dto.request.SyncopeFilterRequestDTO;
import animal.diary.dto.response.FilterResponseDTO;
import animal.diary.repository.FilterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilterService {

    private final FilterRepository filterRepository;

    public FilterResponseDTO filterUrinaryRecords(Long petId, int year, int month, UrinaryFilterRequestDTO filter) {
        log.info("Filtering urinary records for pet: {}, year: {}, month: {}", petId, year, month);

        List<Integer> dates = filterRepository.getUrinaryFilteredDates(petId, year, month, filter);

        return FilterResponseDTO.builder()
                .dates(dates)
                .count(dates.size())
                .build();
    }

    public FilterResponseDTO filterDefecationRecords(Long petId, int year, int month, DefecationFilterRequestDTO filter) {
        log.info("Filtering defecation records for pet: {}, year: {}, month: {}", petId, year, month);

        List<Integer> dates = filterRepository.getDefecationFilteredDates(petId, year, month, filter);

        return FilterResponseDTO.builder()
                .dates(dates)
                .count(dates.size())
                .build();
    }

    public FilterResponseDTO filterConvulsionRecords(Long petId, int year, int month, ConvulsionFilterRequestDTO filter) {
        log.info("Filtering convulsion records for pet: {}, year: {}, month: {}", petId, year, month);

        List<Integer> dates = filterRepository.getConvulsionFilteredDates(petId, year, month, filter);

        return FilterResponseDTO.builder()
                .dates(dates)
                .count(dates.size())
                .build();
    }

    public FilterResponseDTO filterSkinRecords(Long petId, int year, int month, SkinFilterRequestDTO filter) {
        log.info("Filtering skin records for pet: {}, year: {}, month: {}", petId, year, month);

        List<Integer> dates = filterRepository.getSkinFilteredDates(petId, year, month, filter);

        return FilterResponseDTO.builder()
                .dates(dates)
                .count(dates.size())
                .build();
    }

    public FilterResponseDTO filterSnotRecords(Long petId, int year, int month, SnotFilterRequestDTO filter) {
        log.info("Filtering snot records for pet: {}, year: {}, month: {}", petId, year, month);

        List<Integer> dates = filterRepository.getSnotFilteredDates(petId, year, month, filter);

        return FilterResponseDTO.builder()
                .dates(dates)
                .count(dates.size())
                .build();
    }

    public FilterResponseDTO filterVomitingRecords(Long petId, int year, int month, VomitingFilterRequestDTO filter) {
        log.info("Filtering vomiting records for pet: {}, year: {}, month: {}", petId, year, month);

        List<Integer> dates = filterRepository.getVomitingFilteredDates(petId, year, month, filter);

        return FilterResponseDTO.builder()
                .dates(dates)
                .count(dates.size())
                .build();
    }

    public FilterResponseDTO filterWaterRecords(Long petId, int year, int month, WaterFilterRequestDTO filter) {
        log.info("Filtering water records for pet: {}, year: {}, month: {}", petId, year, month);

        List<Integer> dates = filterRepository.getWaterFilteredDates(petId, year, month, filter);

        return FilterResponseDTO.builder()
                .dates(dates)
                .count(dates.size())
                .build();
    }

    public FilterResponseDTO filterAppetiteRecords(Long petId, int year, int month, AppetiteFilterRequestDTO filter) {
        log.info("Filtering appetite records for pet: {}, year: {}, month: {}", petId, year, month);

        List<Integer> dates = filterRepository.getAppetiteFilteredDates(petId, year, month, filter);

        return FilterResponseDTO.builder()
                .dates(dates)
                .count(dates.size())
                .build();
    }

    public FilterResponseDTO filterEnergyRecords(Long petId, int year, int month, EnergyFilterRequestDTO filter) {
        log.info("Filtering energy records for pet: {}, year: {}, month: {}", petId, year, month);

        List<Integer> dates = filterRepository.getEnergyFilteredDates(petId, year, month, filter);

        return FilterResponseDTO.builder()
                .dates(dates)
                .count(dates.size())
                .build();
    }

    public FilterResponseDTO filterSyncopeRecords(Long petId, int year, int month, SyncopeFilterRequestDTO filter) {
        log.info("Filtering syncope records for pet: {}, year: {}, month: {}", petId, year, month);

        List<Integer> dates = filterRepository.getSyncopeFilteredDates(petId, year, month, filter);

        return FilterResponseDTO.builder()
                .dates(dates)
                .count(dates.size())
                .build();
    }
}