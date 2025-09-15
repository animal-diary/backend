package animal.diary.service;

import animal.diary.repository.MonthlyRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonthlyService {

    private final MonthlyRecordRepository monthlyRecordRepository;

    public List<Integer> getRecordDates(Long petId, int year, int month) {
        // 해당 월의 시작과 끝 날짜 설정
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);

        log.info("Searching records for pet {} between {} and {}", petId, startDate, endDate);

        // QueryDSL로 단일 쿼리 실행 - UNION + DISTINCT로 효율적 처리
        List<Integer> recordDates = monthlyRecordRepository.findRecordDatesByPetIdAndMonth(petId, startDate, endDate);

        log.info("Found records on {} days for pet {} in {}-{}", recordDates.size(), petId, year, month);

        return recordDates;
    }
}