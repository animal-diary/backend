package animal.diary.repository;

import animal.diary.entity.record.Urinary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UrinaryRepository extends JpaRepository<Urinary, Long> {
    List<Urinary> findAllByPetIdAndCreatedAtBetween(Long petId, LocalDateTime start, LocalDateTime end);
}