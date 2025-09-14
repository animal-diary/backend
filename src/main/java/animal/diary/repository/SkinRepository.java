package animal.diary.repository;

import animal.diary.entity.record.Skin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SkinRepository extends JpaRepository<Skin, Long> {
    List<Skin> findAllByPetIdAndCreatedAtBetween(Long id, LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
