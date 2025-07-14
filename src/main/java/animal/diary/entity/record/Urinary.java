package animal.diary.entity.record;

import animal.diary.entity.pet.Pet;
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
