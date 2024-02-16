package com.green.greengram4.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_feed")
public class FeedEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long ifeed;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "iuser", nullable = false)
    private UserEntity user;

    @Column(length = 1000)
    private String contents;

    @Column(length = 30)
    private String location;

    @Builder.Default
    @OneToMany(mappedBy = "feedEntity", cascade = CascadeType.ALL)
    private List<FeedPicsEntity> feedPicsEntities = new ArrayList<>();
}

