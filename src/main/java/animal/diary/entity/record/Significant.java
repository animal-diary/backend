package animal.diary.entity.record;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Significant extends Diary{
    private String title;
    private String content;

    private List<String> imageUrls;
}
