package animal.diary.repository;

import animal.diary.entity.record.RespiratoryRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RRRepository extends JpaRepository<RespiratoryRate, Long> {

}
