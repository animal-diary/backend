package animal.diary.entity.record;

import animal.diary.util.StringListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

    @Column(length = 5000)
    @Convert(converter = StringListConverter.class)
    private List<String> imageUrls;

    private String videoUrl;
}
