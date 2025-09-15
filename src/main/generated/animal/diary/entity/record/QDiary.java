package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiary is a Querydsl query type for Diary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiary extends EntityPathBase<Diary> {

    private static final long serialVersionUID = -77619244L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiary diary = new QDiary("diary");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final animal.diary.entity.pet.QPet pet;

    public QDiary(String variable) {
        this(Diary.class, forVariable(variable), INITS);
    }

    public QDiary(Path<? extends Diary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiary(PathMetadata metadata, PathInits inits) {
        this(Diary.class, metadata, inits);
    }

    public QDiary(Class<? extends Diary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pet = inits.isInitialized("pet") ? new animal.diary.entity.pet.QPet(forProperty("pet"), inits.get("pet")) : null;
    }

}

