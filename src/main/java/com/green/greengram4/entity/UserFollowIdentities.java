package com.green.greengram4.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class UserFollowIdentities implements Serializable {

    private Long fromIuser;
    private Long toIuser;
}
