package animal.diary.entity.record;

import animal.diary.entity.record.state.AbnormalState;
import animal.diary.entity.record.state.BinaryState;
import animal.diary.util.AbnormalStateConverter;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Convulsion extends Diary{
    private BinaryState state;

    @Enumerated(EnumType.STRING)
    @Convert(converter = AbnormalStateConverter.class)
    private List<AbnormalState> abnormalState;

    private String imageUrl;
}
