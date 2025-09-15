package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUrinary is a Querydsl query type for Urinary
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUrinary extends EntityPathBase<Urinary> {

    private static final long serialVersionUID = 889923191L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUrinary urinary = new QUrinary("urinary");

    public final QDiary _super;

    public final EnumPath<animal.diary.entity.record.state.BinaryState> binaryState = createEnum("binaryState", animal.diary.entity.record.state.BinaryState.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    public final ListPath<String, StringPath> imageUrls = this.<String, StringPath>createList("imageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt;

    public final StringPath memo = createString("memo");

    public final EnumPath<animal.diary.entity.record.state.LevelState> output = createEnum("output", animal.diary.entity.record.state.LevelState.class);

    // inherited
    public final animal.diary.entity.pet.QPet pet;

    public final EnumPath<animal.diary.entity.record.state.UrineState> state = createEnum("state", animal.diary.entity.record.state.UrineState.class);

    public QUrinary(String variable) {
        this(Urinary.class, forVariable(variable), INITS);
    }

    public QUrinary(Path<? extends Urinary> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUrinary(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUrinary(PathMetadata metadata, PathInits inits) {
        this(Urinary.class, metadata, inits);
    }

    public QUrinary(Class<? extends Urinary> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

