package animal.diary.repository;

import animal.diary.entity.record.RespiratoryRate;
import animal.diary.entity.record.Significant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SignificantRepository extends JpaRepository<Significant, Long> {
    List<Significant> findAllByPetIdAndCreatedAtBetween(Long petId, LocalDateTime start, LocalDateTime end);
}
