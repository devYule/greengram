package com.green.greengram4.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDmUser is a Querydsl query type for DmUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDmUser extends EntityPathBase<DmUser> {

    private static final long serialVersionUID = -632339517L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDmUser dmUser = new QDmUser("dmUser");

    public final QDmEntity dmEntity;

    public final QDmUserIdentities dmUserIdentities;

    public final QUserEntity userEntity;

    public QDmUser(String variable) {
        this(DmUser.class, forVariable(variable), INITS);
    }

    public QDmUser(Path<? extends DmUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDmUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDmUser(PathMetadata metadata, PathInits inits) {
        this(DmUser.class, metadata, inits);
    }

    public QDmUser(Class<? extends DmUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dmEntity = inits.isInitialized("dmEntity") ? new QDmEntity(forProperty("dmEntity")) : null;
        this.dmUserIdentities = inits.isInitialized("dmUserIdentities") ? new QDmUserIdentities(forProperty("dmUserIdentities")) : null;
        this.userEntity = inits.isInitialized("userEntity") ? new QUserEntity(forProperty("userEntity")) : null;
    }

}

