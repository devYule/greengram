package com.green.greengram4.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_dm")
public class DmEntity extends CreatedAtEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long idm;

    @Column(length = 2000)
    private String lastMsg;

    @UpdateTimestamp
    private LocalDateTime lastMsgAt;

}
