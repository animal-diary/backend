package animal.diary.entity.record;

import animal.diary.entity.record.state.AbnormalState;
import animal.diary.entity.record.state.BinaryState;
import animal.diary.util.AbnormalStateConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Convulsion extends Diary{
    private BinaryState state;

    @Enumerated(EnumType.STRING)
    @Convert(converter = AbnormalStateConverter.class)
    private List<AbnormalState> abnormalState;

    private String imageUrl;
}
