package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDmMsgIdentities is a Querydsl query type for DmMsgIdentities
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDmMsgIdentities extends BeanPath<DmMsgIdentities> {

    private static final long serialVersionUID = -1636106427L;

    public static final QDmMsgIdentities dmMsgIdentities = new QDmMsgIdentities("dmMsgIdentities");

    public final NumberPath<Long> idm = createNumber("idm", Long.class);

    public final NumberPath<Long> seq = createNumber("seq", Long.class);

    public QDmMsgIdentities(String variable) {
        super(DmMsgIdentities.class, forVariable(variable));
    }

    public QDmMsgIdentities(Path<? extends DmMsgIdentities> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDmMsgIdentities(PathMetadata metadata) {
        super(DmMsgIdentities.class, metadata);
    }

}

