package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user_follow")
public class UserFollow extends CreatedAtEntity {
    @EmbeddedId
    private UserFollowIdentities userFollowIdentities;

    @ManyToOne
    @MapsId("fromIuser")
    @JoinColumn(name = "from_iuser", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private UserEntity fromUserEntity;

    @ManyToOne
    @MapsId("toIuser")
    @JoinColumn(name = "to_iuser", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private UserEntity toUserEntity;

}
