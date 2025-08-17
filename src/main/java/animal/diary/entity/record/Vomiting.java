package animal.diary.entity.record;

import animal.diary.entity.record.state.BinaryState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Vomiting extends Diary{
    @Schema(description = "상태", example = "O, X")
    private BinaryState state;

    @Schema(description = "토 이미지", example = "https://example.com/vomiting1.jpg, https://example.com/vomiting2.jpg")
    private List<String> imageFiles;
}
