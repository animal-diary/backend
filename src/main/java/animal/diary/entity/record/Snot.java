package animal.diary.entity.record;

import animal.diary.entity.record.state.SnotState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Snot extends Diary{
    @Schema(description = "코 분비물 상태", example = "NORMAL, ABNORMAL")
    private SnotState state;
    @Schema(description = "메모", example = "코 분비물이 많아 보입니다.")
    private String memo;
    @Schema(description = "코 분비물 이미지 URL 목록", example = "https://example.com/snot1.jpg, https://example.com/snot2.jpg")
    private List<String> imageUrls;
}
