package animal.diary.entity.record;

import animal.diary.entity.record.state.BinaryState;
import animal.diary.entity.record.state.LevelState;
import animal.diary.entity.record.state.UrineState;
import animal.diary.util.StringListConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
public class Urinary extends Diary {
    @Enumerated(EnumType.STRING)
    private UrineState state;

    @Enumerated(EnumType.STRING)
    private LevelState output;

    @Enumerated(EnumType.STRING)
    private BinaryState binaryState;

    @Size(max = 200)
    private String memo; // 메모

    @Size(max = 5000)
    @Convert(converter = StringListConverter.class)
    private List<String> imageUrls; // 이미지 URL 목록
}
