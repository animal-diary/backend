package animal.diary.repository;

import animal.diary.entity.record.Water;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaterRepository extends JpaRepository<Water, Long> {
}
