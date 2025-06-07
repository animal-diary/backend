package animal.diary.repository;

import animal.diary.entity.record.Energy;
import animal.diary.entity.record.RespiratoryRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RRRepository extends JpaRepository<RespiratoryRate, Long> {
    List<RespiratoryRate> findAllByPetIdAndCreatedAtBetween(Long petId, LocalDateTime start, LocalDateTime end);
}
