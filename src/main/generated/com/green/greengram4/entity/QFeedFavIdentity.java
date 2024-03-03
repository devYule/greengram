package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFeedFavIdentity is a Querydsl query type for FeedFavIdentity
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QFeedFavIdentity extends BeanPath<FeedFavIdentity> {

    private static final long serialVersionUID = -949396084L;

    public static final QFeedFavIdentity feedFavIdentity = new QFeedFavIdentity("feedFavIdentity");

    public final NumberPath<Long> ifeed = createNumber("ifeed", Long.class);

    public final NumberPath<Long> iuser = createNumber("iuser", Long.class);

    public QFeedFavIdentity(String variable) {
        super(FeedFavIdentity.class, forVariable(variable));
    }

    public QFeedFavIdentity(Path<? extends FeedFavIdentity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFeedFavIdentity(PathMetadata metadata) {
        super(FeedFavIdentity.class, metadata);
    }

}

