package animal.diary.repository;

import animal.diary.entity.record.Sound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SoundRepository extends JpaRepository<Sound, Long> {
    List<Sound> findAllByPetIdAndCreatedAtBetween(Long id, LocalDateTime start, LocalDateTime end);

    // Custom query methods can be defined here if needed
    // For example, to find sounds by a specific attribute:
    // List<Sound> findByAttribute(String attribute);
}
