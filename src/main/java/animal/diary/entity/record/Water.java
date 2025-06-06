package animal.diary.entity.record;

import jakarta.persistence.Entity;

@Entity
public class Water extends Diary {
    private LevelState state;
}
