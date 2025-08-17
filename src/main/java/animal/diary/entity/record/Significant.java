package animal.diary.entity.record;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
public class Significant extends Diary{
    private String title;
    private String content;

    private List<String> imageUrls;
}
