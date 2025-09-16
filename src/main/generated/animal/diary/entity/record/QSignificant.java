package animal.diary.entity.record;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSignificant is a Querydsl query type for Significant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSignificant extends EntityPathBase<Significant> {

    private static final long serialVersionUID = 2125630084L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSignificant significant = new QSignificant("significant");

    public final QDiary _super;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final NumberPath<Long> id;

    public final ListPath<String, StringPath> imageUrls = this.<String, StringPath>createList("imageUrls", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath imageUrlsRaw = createString("imageUrlsRaw");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt;

    // inherited
    public final animal.diary.entity.pet.QPet pet;

    public final StringPath title = createString("title");

    public final StringPath videoUrl = createString("videoUrl");

    public QSignificant(String variable) {
        this(Significant.class, forVariable(variable), INITS);
    }

    public QSignificant(Path<? extends Significant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSignificant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSignificant(PathMetadata metadata, PathInits inits) {
        this(Significant.class, metadata, inits);
    }

    public QSignificant(Class<? extends Significant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QDiary(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.id = _super.id;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.pet = _super.pet;
    }

}

