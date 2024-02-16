package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_feed_pics")
public class FeedPicsEntity extends CreatedAtEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ifeedPics;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ifeed")
    private FeedEntity feedEntity;

    @Column(length = 2100)
    private String pic;

}