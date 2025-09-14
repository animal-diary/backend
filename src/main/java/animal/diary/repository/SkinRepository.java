package animal.diary.repository;

import animal.diary.entity.record.Skin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkinRepository extends JpaRepository<Skin, Long> {
}
