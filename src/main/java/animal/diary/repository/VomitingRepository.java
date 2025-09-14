package animal.diary.repository;

import animal.diary.entity.record.Vomiting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VomitingRepository extends JpaRepository<Vomiting, Long> {
}
