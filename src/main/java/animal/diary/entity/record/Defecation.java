package animal.diary.entity.record;

import animal.diary.entity.record.state.StoolState;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Defecation extends Diary{
    private StoolState state;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "defaction_id")  // 💡 여기에 들어갈 이름!
    private List<File> imageFiles;
}
