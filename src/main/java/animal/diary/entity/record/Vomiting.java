package animal.diary.entity.record;

import animal.diary.entity.record.state.BinaryState;
import animal.diary.util.StringListConverter;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class Vomiting extends Diary{
    @Schema(description = "상태", example = "O, X")
    private BinaryState state;

    @Schema(description = "토 이미지", example = "https://example.com/vomiting1.jpg, https://example.com/vomiting2.jpg")
    @Convert(converter = StringListConverter.class)
    private List<String> imageUrls;
}
