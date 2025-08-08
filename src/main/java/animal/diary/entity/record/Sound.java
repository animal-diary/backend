package animal.diary.entity.record;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.util.List;

@Entity
public class Sound extends Diary{
    private String title;

    private List<String> imageUrls;
}
