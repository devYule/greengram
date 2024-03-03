package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserFollow is a Querydsl query type for UserFollow
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserFollow extends EntityPathBase<UserFollow> {

    private static final long serialVersionUID = -1669622869L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserFollow userFollow = new QUserFollow("userFollow");

    public final QCreatedAtEntity _super = new QCreatedAtEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QUserEntity fromUserEntity;

    public final QUserEntity toUserEntity;

    public final QUserFollowIdentities userFollowIdentities;

    public QUserFollow(String variable) {
        this(UserFollow.class, forVariable(variable), INITS);
    }

    public QUserFollow(Path<? extends UserFollow> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserFollow(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserFollow(PathMetadata metadata, PathInits inits) {
        this(UserFollow.class, metadata, inits);
    }

    public QUserFollow(Class<? extends UserFollow> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fromUserEntity = inits.isInitialized("fromUserEntity") ? new QUserEntity(forProperty("fromUserEntity")) : null;
        this.toUserEntity = inits.isInitialized("toUserEntity") ? new QUserEntity(forProperty("toUserEntity")) : null;
        this.userFollowIdentities = inits.isInitialized("userFollowIdentities") ? new QUserFollowIdentities(forProperty("userFollowIdentities")) : null;
    }

}

