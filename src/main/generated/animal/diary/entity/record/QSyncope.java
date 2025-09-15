package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSyncope is a Querydsl query type for Syncope
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSyncope extends EntityPathBase<Syncope> {

    private static final long serialVersionUID = -680376838L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSyncope syncope = new QSyncope("syncope");

    public final QDiary _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt;

    // inherited
    public final animal.diary.entity.pet.QPet pet;

    public final EnumPath<animal.diary.entity.record.state.BinaryState> state = createEnum("state", animal.diary.entity.record.state.BinaryState.class);

    public QSyncope(String variable) {
        this(Syncope.class, forVariable(variable), INITS);
    }

    public QSyncope(Path<? extends Syncope> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSyncope(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSyncope(PathMetadata metadata, PathInits inits) {
        this(Syncope.class, metadata, inits);
    }

    public QSyncope(Class<? extends Syncope> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

