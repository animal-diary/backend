package animal.diary.entity.record;

import animal.diary.entity.record.state.LevelState;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
public class Appetite extends Diary{

    @Getter
    private LevelState state;
}
