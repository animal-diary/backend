package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSnot is a Querydsl query type for Snot
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSnot extends EntityPathBase<Snot> {

    private static final long serialVersionUID = -556241073L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSnot snot = new QSnot("snot");

    public final QDiary _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    public final ListPath<String, StringPath> imageUrls = this.<String, StringPath>createList("imageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath imageUrlsRaw = createString("imageUrlsRaw");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt;

    public final StringPath memo = createString("memo");

    // inherited
    public final animal.diary.entity.pet.QPet pet;

    public final EnumPath<animal.diary.entity.record.state.SnotState> state = createEnum("state", animal.diary.entity.record.state.SnotState.class);

    public QSnot(String variable) {
        this(Snot.class, forVariable(variable), INITS);
    }

    public QSnot(Path<? extends Snot> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSnot(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSnot(PathMetadata metadata, PathInits inits) {
        this(Snot.class, metadata, inits);
    }

    public QSnot(Class<? extends Snot> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

