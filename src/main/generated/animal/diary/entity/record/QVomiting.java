package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVomiting is a Querydsl query type for Vomiting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVomiting extends EntityPathBase<Vomiting> {

    private static final long serialVersionUID = 1008570578L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVomiting vomiting = new QVomiting("vomiting");

    public final QDiary _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    public final ListPath<String, StringPath> imageUrls = this.<String, StringPath>createList("imageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt;

    // inherited
    public final animal.diary.entity.pet.QPet pet;

    public final EnumPath<animal.diary.entity.record.state.BinaryState> state = createEnum("state", animal.diary.entity.record.state.BinaryState.class);

    public QVomiting(String variable) {
        this(Vomiting.class, forVariable(variable), INITS);
    }

    public QVomiting(Path<? extends Vomiting> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVomiting(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVomiting(PathMetadata metadata, PathInits inits) {
        this(Vomiting.class, metadata, inits);
    }

    public QVomiting(Class<? extends Vomiting> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

