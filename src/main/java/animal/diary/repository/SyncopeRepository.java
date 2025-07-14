package animal.diary.repository;

import animal.diary.entity.record.Syncope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SyncopeRepository extends JpaRepository<Syncope, Long> {
    List<Syncope> findAllByPetIdAndCreatedAtBetween(Long petId, LocalDateTime start, LocalDateTime end);
}