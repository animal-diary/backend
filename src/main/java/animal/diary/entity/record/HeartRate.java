package animal.diary.entity.record;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
public class HeartRate extends Diary{

    @Getter
    private Integer count;
}
