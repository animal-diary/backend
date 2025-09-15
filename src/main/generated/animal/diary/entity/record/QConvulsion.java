package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConvulsion is a Querydsl query type for Convulsion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConvulsion extends EntityPathBase<Convulsion> {

    private static final long serialVersionUID = -1560483697L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConvulsion convulsion = new QConvulsion("convulsion");

    public final QDiary _super;

    public final ListPath<animal.diary.entity.record.state.AbnormalState, EnumPath<animal.diary.entity.record.state.AbnormalState>> abnormalState = this.<animal.diary.entity.record.state.AbnormalState, EnumPath<animal.diary.entity.record.state.AbnormalState>>createList("abnormalState", animal.diary.entity.record.state.AbnormalState.class, EnumPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt;

    // inherited
    public final animal.diary.entity.pet.QPet pet;

    public final EnumPath<animal.diary.entity.record.state.BinaryState> state = createEnum("state", animal.diary.entity.record.state.BinaryState.class);

    public final StringPath videoUrl = createString("videoUrl");

    public QConvulsion(String variable) {
        this(Convulsion.class, forVariable(variable), INITS);
    }

    public QConvulsion(Path<? extends Convulsion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConvulsion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConvulsion(PathMetadata metadata, PathInits inits) {
        this(Convulsion.class, metadata, inits);
    }

    public QConvulsion(Class<? extends Convulsion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

