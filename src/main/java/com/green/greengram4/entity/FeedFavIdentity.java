package com.green.greengram4.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Embeddable
public class FeedFavIdentity implements Serializable {
    private Long iuser;
    private Long ifeed;
}
