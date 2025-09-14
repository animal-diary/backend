package animal.diary.repository;

import animal.diary.entity.record.Defecation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DefecationRepository extends JpaRepository<Defecation, Long> {
    List<Defecation> findAllByPetIdAndCreatedAtBetween(Long id, LocalDateTime localDateTime, LocalDateTime localDateTime1);
}