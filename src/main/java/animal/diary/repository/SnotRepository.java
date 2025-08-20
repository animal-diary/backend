package animal.diary.repository;

import animal.diary.entity.record.Snot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SnotRepository extends JpaRepository<Snot, Long> {
    List<Snot> findAllByPetIdAndCreatedAtBetween(Long petId, LocalDateTime start, LocalDateTime end);
}
