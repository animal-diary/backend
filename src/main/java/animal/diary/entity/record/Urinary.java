package animal.diary.entity.record;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Urinary extends Diary{
    @Enumerated(EnumType.STRING)
    private UrineState state;

    @Enumerated(EnumType.STRING)
    private LevelState output;

}
