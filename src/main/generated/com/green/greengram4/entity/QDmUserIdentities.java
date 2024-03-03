package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDmUserIdentities is a Querydsl query type for DmUserIdentities
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDmUserIdentities extends BeanPath<DmUserIdentities> {

    private static final long serialVersionUID = -554921473L;

    public static final QDmUserIdentities dmUserIdentities = new QDmUserIdentities("dmUserIdentities");

    public final NumberPath<Long> idm = createNumber("idm", Long.class);

    public final NumberPath<Long> iuser = createNumber("iuser", Long.class);

    public QDmUserIdentities(String variable) {
        super(DmUserIdentities.class, forVariable(variable));
    }

    public QDmUserIdentities(Path<? extends DmUserIdentities> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDmUserIdentities(PathMetadata metadata) {
        super(DmUserIdentities.class, metadata);
    }

}

