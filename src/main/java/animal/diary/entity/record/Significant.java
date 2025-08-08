package animal.diary.entity.record;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Significant extends Diary{
    private String title;
    private String content;

    private List<String> imageUrls;
}
