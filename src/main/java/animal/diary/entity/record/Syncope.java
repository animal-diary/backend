package animal.diary.entity.record;

import animal.diary.entity.record.state.BinaryState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Syncope extends Diary{
    @Enumerated(EnumType.STRING)
    private BinaryState state;
}
