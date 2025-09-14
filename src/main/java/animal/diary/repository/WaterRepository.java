package animal.diary.repository;

import animal.diary.entity.record.Water;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WaterRepository extends JpaRepository<Water, Long> {
    List<Water> findAllByPetIdAndCreatedAtBetween(Long id, LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
