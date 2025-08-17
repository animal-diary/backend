package animal.diary.entity.record;

import animal.diary.entity.record.state.StoolState;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Defecation extends Diary{
    private StoolState state;

    private List<String> imageUrls;
}
