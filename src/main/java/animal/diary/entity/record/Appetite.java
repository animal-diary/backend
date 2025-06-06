package animal.diary.entity.record;

import jakarta.persistence.Entity;

@Entity
public class Appetite extends Diary{
    private LevelState state;
}
