package animal.diary.entity.record;

import animal.diary.entity.record.state.NumberState;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Skin extends Diary{
    private NumberState state;

    private List<String> imageUrls;
}
