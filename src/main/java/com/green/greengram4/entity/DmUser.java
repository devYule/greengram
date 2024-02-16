package com.green.greengram4.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_dm_user")
public class DmUser {

    @EmbeddedId
    private DmUserIdentities dmUserIdentities;


    @ManyToOne
    @JoinColumn(name = "idm", columnDefinition = "BIGINT UNSIGNED")
    @MapsId("idm")
    private DmEntity dmEntity;

    @ManyToOne
    @JoinColumn(name = "iuser", columnDefinition = "BIGINT UNSIGNED")
    @MapsId("iuser")
    private UserEntity userEntity;


}
