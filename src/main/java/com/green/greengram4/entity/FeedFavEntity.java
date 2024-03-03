package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_feed_fav")
public class FeedFavEntity extends CreatedAtEntity{

    @EmbeddedId
    FeedFavIdentity feedFavIdentity;

    @MapsId("iuser")
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "iuser", columnDefinition = "BIGINT UNSIGNED")
    private UserEntity userEntity;

    @MapsId("ifeed")
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "ifeed", columnDefinition = "BIGINT UNSIGNED")
    private FeedEntity feedEntity;

}
