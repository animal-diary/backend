package animal.diary.repository;

import animal.diary.entity.record.Appetite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppetiteRepository extends JpaRepository<Appetite, Long>{
    List<Appetite> findAllByPetIdAndCreatedAtBetween(Long petId, LocalDateTime start, LocalDateTime end);
}
