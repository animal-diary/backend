package animal.diary.entity.record;

import animal.diary.entity.record.state.SnotState;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Snot extends Diary{
    private SnotState state;
    private String memo;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "snot_id")  // 💡 여기에 들어갈 이름!
    private List<File> imageFiles;
}
