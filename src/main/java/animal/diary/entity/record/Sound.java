package animal.diary.entity.record;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Sound extends Diary{
    private String title;

    private List<String> imageUrls;
}
