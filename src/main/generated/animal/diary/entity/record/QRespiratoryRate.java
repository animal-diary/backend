package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRespiratoryRate is a Querydsl query type for RespiratoryRate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRespiratoryRate extends EntityPathBase<RespiratoryRate> {

    private static final long serialVersionUID = 1961908059L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRespiratoryRate respiratoryRate = new QRespiratoryRate("respiratoryRate");

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

    public QRespiratoryRate(String variable) {
        this(RespiratoryRate.class, forVariable(variable), INITS);
    }

    public QRespiratoryRate(Path<? extends RespiratoryRate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRespiratoryRate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRespiratoryRate(PathMetadata metadata, PathInits inits) {
        this(RespiratoryRate.class, metadata, inits);
    }

    public QRespiratoryRate(Class<? extends RespiratoryRate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

