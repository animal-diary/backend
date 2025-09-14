package animal.diary.repository;

import animal.diary.entity.record.Walking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WalkingRepository extends JpaRepository<Walking, Long> {
    List<Walking> findAllByPetIdAndCreatedAtBetween(Long id, LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
