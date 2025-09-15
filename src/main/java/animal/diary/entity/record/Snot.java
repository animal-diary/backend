package animal.diary.entity.record;

import animal.diary.entity.record.state.SnotState;
import animal.diary.util.StringListConverter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Formula;

import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
public class Snot extends Diary{
    private SnotState state;
    private String memo;

    @Column(length = 5000)
    @Convert(converter = StringListConverter.class)
    private List<String> imageUrls;

    @Formula("image_urls")
    private String imageUrlsRaw;
}
