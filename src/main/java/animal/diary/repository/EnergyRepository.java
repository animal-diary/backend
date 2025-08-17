package animal.diary.repository;

import animal.diary.entity.record.Energy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EnergyRepository extends JpaRepository<Energy, Long> {
    List<Energy> findAllByPetIdAndCreatedAtBetween(Long petId, LocalDateTime start, LocalDateTime end);
}
