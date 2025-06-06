package animal.diary.repository;

import animal.diary.entity.record.Energy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergyRepository extends JpaRepository<Energy, Long> {

}
