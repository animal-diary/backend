package animal.diary.entity.record;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Skin extends Diary{
    private NumberState state;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "skin_id")  // 💡 여기에 들어갈 이름!
    private List<File> imageFiles;
}
