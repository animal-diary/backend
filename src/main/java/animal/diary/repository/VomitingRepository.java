package animal.diary.repository;

import animal.diary.entity.record.Vomiting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VomitingRepository extends JpaRepository<Vomiting, Long> {
    List<Vomiting> findAllByPetIdAndCreatedAtBetween(Long id, LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
