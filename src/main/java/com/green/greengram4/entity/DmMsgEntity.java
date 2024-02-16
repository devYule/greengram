package com.green.greengram4.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "t_dm_msg")
public class DmMsgEntity extends CreatedAtEntity {

    @EmbeddedId
    private DmMsgIdentities dmMsgIdentities;

    @MapsId("idm")
    @ManyToOne
    @JoinColumn(name = "idm", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private DmEntity dmEntity;

    @ManyToOne
    @JoinColumn(name = "iuser", nullable = false)
    private UserEntity userEntity;

    @Column(length = 2000, nullable = false)
    private String msg;

}
