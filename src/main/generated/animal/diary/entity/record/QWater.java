package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWater is a Querydsl query type for Water
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWater extends EntityPathBase<Water> {

    private static final long serialVersionUID = -60292824L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWater water = new QWater("water");

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

    public QWater(String variable) {
        this(Water.class, forVariable(variable), INITS);
    }

    public QWater(Path<? extends Water> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWater(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWater(PathMetadata metadata, PathInits inits) {
        this(Water.class, metadata, inits);
    }

    public QWater(Class<? extends Water> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

