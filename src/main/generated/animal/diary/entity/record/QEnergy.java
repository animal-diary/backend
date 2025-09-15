package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEnergy is a Querydsl query type for Energy
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEnergy extends EntityPathBase<Energy> {

    private static final long serialVersionUID = 1922136215L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEnergy energy = new QEnergy("energy");

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

    public QEnergy(String variable) {
        this(Energy.class, forVariable(variable), INITS);
    }

    public QEnergy(Path<? extends Energy> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEnergy(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEnergy(PathMetadata metadata, PathInits inits) {
        this(Energy.class, metadata, inits);
    }

    public QEnergy(Class<? extends Energy> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

