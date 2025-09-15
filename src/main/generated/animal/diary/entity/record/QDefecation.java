package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDefecation is a Querydsl query type for Defecation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDefecation extends EntityPathBase<Defecation> {

    private static final long serialVersionUID = -353049599L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDefecation defecation = new QDefecation("defecation");

    public final QDiary _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    public final ListPath<String, StringPath> imageUrls = this.<String, StringPath>createList("imageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt;

    public final EnumPath<animal.diary.entity.record.state.LevelState> level = createEnum("level", animal.diary.entity.record.state.LevelState.class);

    public final StringPath memo = createString("memo");

    // inherited
    public final animal.diary.entity.pet.QPet pet;

    public final EnumPath<animal.diary.entity.record.state.StoolState> state = createEnum("state", animal.diary.entity.record.state.StoolState.class);

    public QDefecation(String variable) {
        this(Defecation.class, forVariable(variable), INITS);
    }

    public QDefecation(Path<? extends Defecation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDefecation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDefecation(PathMetadata metadata, PathInits inits) {
        this(Defecation.class, metadata, inits);
    }

    public QDefecation(Class<? extends Defecation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

