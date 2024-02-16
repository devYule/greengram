package com.green.greengram4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Embeddable
@EqualsAndHashCode
public class DmMsgIdentities implements Serializable {

    private Long idm;
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long seq;
}
