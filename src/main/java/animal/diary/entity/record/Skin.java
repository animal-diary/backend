package animal.diary.entity.record;

import animal.diary.entity.record.state.NumberState;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Skin extends Diary{
    private NumberState state;

    private List<String> imageUrls;
}
