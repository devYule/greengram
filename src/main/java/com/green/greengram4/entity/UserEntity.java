package com.green.greengram4.entity;

import com.green.greengram4.common.Role;
import com.green.greengram4.security.oauth2.SocialProviderType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "uid", "provider_type"
        })
})
@DynamicInsert // 값이 null 이면 insert 에 포함하지 않음.
public class UserEntity extends BaseEntity {

    @Id
    @Column(columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iuser;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    @ColumnDefault("'LOCAL'")
    private SocialProviderType providerType;

    @Column(length = 100, nullable = false)
    private String uid;

    @Column(length = 2000, nullable = false)
    private String upw;

    @Column(length = 25)
    private String nm;

    @Column(length = 2100)
    private String pic;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private Role role;

    @Column(length = 2100)
    private String firebaseToken;




}
