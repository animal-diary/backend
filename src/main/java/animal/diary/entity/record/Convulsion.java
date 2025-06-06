package animal.diary.entity.record;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Convulsion extends Diary{
    private BinaryState state;

    @ElementCollection(targetClass = AbnormalState.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "observation_abnormal_states", joinColumns = @JoinColumn(name = "observation_id"))
    @Column(name = "state")
    private List<AbnormalState> abnormalState;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "file_id")
    private File file;
}
