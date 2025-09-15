package animal.diary.service;

import animal.diary.dto.request.UrinaryFilterRequestDTO;
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
}