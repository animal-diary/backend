package animal.diary.entity.record;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(description = "걷는 모습 기록 엔티티")
public class Walking extends Diary{
    @Schema(description = "제목", example = "오늘의 산책")
    private String title;
    @Schema(description = "걷는 영상", example = "https://example.com/walking.mp4")
    private String imageUrl;
}
