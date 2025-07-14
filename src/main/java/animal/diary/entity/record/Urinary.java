package animal.diary.entity.record;

import animal.diary.entity.record.state.LevelState;
import animal.diary.entity.record.state.UrineState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
public class Urinary extends Diary {
    @Enumerated(EnumType.STRING)
    private UrineState state;

    @Enumerated(EnumType.STRING)
    private LevelState output;
}
