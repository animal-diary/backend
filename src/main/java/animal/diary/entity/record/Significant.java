package animal.diary.entity.record;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Entity
@SuperBuilder
@NoArgsConstructor
public class Significant extends Diary{
    @Getter
    private String title;
    @Getter
    private String content;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter
    @JoinColumn(name = "significant_id")  // 💡 여기에 들어갈 이름!
    private List<File> imageFiles;
}
