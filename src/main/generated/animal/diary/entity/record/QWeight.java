package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWeight is a Querydsl query type for Weight
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWeight extends EntityPathBase<Weight> {

    private static final long serialVersionUID = -1865709433L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWeight weight1 = new QWeight("weight1");

    public final QDiary _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt;

    // inherited
    public final animal.diary.entity.pet.QPet pet;

    public final NumberPath<Float> weight = createNumber("weight", Float.class);

    public QWeight(String variable) {
        this(Weight.class, forVariable(variable), INITS);
    }

    public QWeight(Path<? extends Weight> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWeight(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWeight(PathMetadata metadata, PathInits inits) {
        this(Weight.class, metadata, inits);
    }

    public QWeight(Class<? extends Weight> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

