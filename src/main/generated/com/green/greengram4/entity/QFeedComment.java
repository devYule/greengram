package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeedComment is a Querydsl query type for FeedComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeedComment extends EntityPathBase<FeedComment> {

    private static final long serialVersionUID = -1869121422L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeedComment feedComment = new QFeedComment("feedComment");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath comment = createString("comment");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QFeedEntity feedEntity;

    public final NumberPath<Long> ifeedComment = createNumber("ifeedComment", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QUserEntity userEntity;

    public QFeedComment(String variable) {
        this(FeedComment.class, forVariable(variable), INITS);
    }

    public QFeedComment(Path<? extends FeedComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeedComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeedComment(PathMetadata metadata, PathInits inits) {
        this(FeedComment.class, metadata, inits);
    }

    public QFeedComment(Class<? extends FeedComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.feedEntity = inits.isInitialized("feedEntity") ? new QFeedEntity(forProperty("feedEntity"), inits.get("feedEntity")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity")) : null;
    }

}

