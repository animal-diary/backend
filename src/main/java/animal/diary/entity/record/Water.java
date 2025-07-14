package animal.diary.entity.record;

import animal.diary.entity.record.state.LevelState;
import jakarta.persistence.Entity;

@Entity
public class Water extends Diary {
    private LevelState state;
}
