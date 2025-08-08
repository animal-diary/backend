package animal.diary.entity.record;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "걷는 모습 기록 엔티티")
public class Walking extends Diary{
    @Schema(description = "제목", example = "오늘의 산책")
    private String title;
    @Schema(description = "걷는 영상", example = "https://example.com/walking.mp4")
    private String imageUrl;
}
