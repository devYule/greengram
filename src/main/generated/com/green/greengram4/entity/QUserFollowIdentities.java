package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserFollowIdentities is a Querydsl query type for UserFollowIdentities
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUserFollowIdentities extends BeanPath<UserFollowIdentities> {

    private static final long serialVersionUID = 1552162791L;

    public static final QUserFollowIdentities userFollowIdentities = new QUserFollowIdentities("userFollowIdentities");

    public final NumberPath<Long> fromIuser = createNumber("fromIuser", Long.class);

    public final NumberPath<Long> toIuser = createNumber("toIuser", Long.class);

    public QUserFollowIdentities(String variable) {
        super(UserFollowIdentities.class, forVariable(variable));
    }

    public QUserFollowIdentities(Path<? extends UserFollowIdentities> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserFollowIdentities(PathMetadata metadata) {
        super(UserFollowIdentities.class, metadata);
    }

}

