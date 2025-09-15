package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAppetite is a Querydsl query type for Appetite
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAppetite extends EntityPathBase<Appetite> {

    private static final long serialVersionUID = -261010855L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAppetite appetite = new QAppetite("appetite");

    public final QDiary _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt;

    // inherited
    public final animal.diary.entity.pet.QPet pet;

    public final EnumPath<animal.diary.entity.record.state.LevelState> state = createEnum("state", animal.diary.entity.record.state.LevelState.class);

    public QAppetite(String variable) {
        this(Appetite.class, forVariable(variable), INITS);
    }

    public QAppetite(Path<? extends Appetite> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAppetite(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAppetite(PathMetadata metadata, PathInits inits) {
        this(Appetite.class, metadata, inits);
    }

    public QAppetite(Class<? extends Appetite> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

