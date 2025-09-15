package animal.diary.entity.record;

import animal.diary.util.StringListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Formula;

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

    @Formula("image_urls")
    private String imageUrlsRaw;

    private String videoUrl;
}
