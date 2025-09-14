package animal.diary.repository;

import animal.diary.entity.record.Walking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalkingRepository extends JpaRepository<Walking, Long> {
}
