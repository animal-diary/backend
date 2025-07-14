package animal.diary.repository;

import animal.diary.entity.record.HeartRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HeartRateRepository extends JpaRepository<HeartRate, Long> {
    List<HeartRate> findAllByPetIdAndCreatedAtBetween(Long id, LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
