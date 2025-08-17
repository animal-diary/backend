package animal.diary.repository;

import animal.diary.entity.record.Sound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoundRepository extends JpaRepository<Sound, Long> {

    // Custom query methods can be defined here if needed
    // For example, to find sounds by a specific attribute:
    // List<Sound> findByAttribute(String attribute);
}
