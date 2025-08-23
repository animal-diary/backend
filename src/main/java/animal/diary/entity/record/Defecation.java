package animal.diary.entity.record;

import animal.diary.entity.record.state.LevelState;
import animal.diary.entity.record.state.StoolState;
import animal.diary.util.StringListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
public class Defecation extends Diary{
    private LevelState level;
    private StoolState state;
    private String memo;

    @Column(length = 5000)
    @Convert(converter = StringListConverter.class)
    private List<String> imageUrls;
}
