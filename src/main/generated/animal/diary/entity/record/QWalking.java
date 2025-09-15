package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWalking is a Querydsl query type for Walking
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalking extends EntityPathBase<Walking> {

    private static final long serialVersionUID = -2114043574L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWalking walking = new QWalking("walking");

    public final QDiary _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt;

    // inherited
    public final animal.diary.entity.pet.QPet pet;

    public final StringPath title = createString("title");

    public QWalking(String variable) {
        this(Walking.class, forVariable(variable), INITS);
    }

    public QWalking(Path<? extends Walking> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWalking(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWalking(PathMetadata metadata, PathInits inits) {
        this(Walking.class, metadata, inits);
    }

    public QWalking(Class<? extends Walking> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

