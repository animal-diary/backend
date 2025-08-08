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

    private List<String> imageUrls;
}
