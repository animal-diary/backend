package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHeartRate is a Querydsl query type for HeartRate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHeartRate extends EntityPathBase<HeartRate> {

    private static final long serialVersionUID = -1315903017L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHeartRate heartRate = new QHeartRate("heartRate");

    public final QDiary _super;

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt;

    // inherited
    public final animal.diary.entity.pet.QPet pet;

    public QHeartRate(String variable) {
        this(HeartRate.class, forVariable(variable), INITS);
    }

    public QHeartRate(Path<? extends HeartRate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHeartRate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHeartRate(PathMetadata metadata, PathInits inits) {
        this(HeartRate.class, metadata, inits);
    }

    public QHeartRate(Class<? extends HeartRate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

