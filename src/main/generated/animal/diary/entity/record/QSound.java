package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSound is a Querydsl query type for Sound
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSound extends EntityPathBase<Sound> {

    private static final long serialVersionUID = -63568608L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSound sound = new QSound("sound");

    public final QDiary _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    public final StringPath imageUrl = createString("imageUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt;

    // inherited
    public final animal.diary.entity.pet.QPet pet;

    public final StringPath title = createString("title");

    public QSound(String variable) {
        this(Sound.class, forVariable(variable), INITS);
    }

    public QSound(Path<? extends Sound> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSound(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSound(PathMetadata metadata, PathInits inits) {
        this(Sound.class, metadata, inits);
    }

    public QSound(Class<? extends Sound> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

