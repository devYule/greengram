package com.green.greengram4.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class DmUserIdentities implements Serializable {

    private Long idm;
    private Long iuser;


}
