package animal.diary.repository;

import animal.diary.entity.record.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WeightRepository extends JpaRepository<Weight, Long> {
    List<Weight> findAllByPetIdAndCreatedAtBetween(Long petId, LocalDateTime start, LocalDateTime end);
}
