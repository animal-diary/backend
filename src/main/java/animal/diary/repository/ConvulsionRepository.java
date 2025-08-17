package animal.diary.repository;

import animal.diary.entity.record.Convulsion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ConvulsionRepository extends JpaRepository<Convulsion, Long> {
    List<Convulsion> findAllByPetIdAndCreatedAtBetween(Long petId, LocalDateTime start, LocalDateTime end);
}