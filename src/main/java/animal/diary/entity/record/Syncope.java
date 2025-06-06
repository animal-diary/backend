package animal.diary.entity.record;

import jakarta.persistence.Entity;

@Entity
public class Syncope extends Diary{
    private BinaryState state;
}
