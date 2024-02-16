package com.green.greengram4.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class) // 공통처리 이벤트 리스너 - 자동으로 now, on update 를 넣어준다.
@MappedSuperclass
public class BaseEntity extends CreatedAtEntity{

    @LastModifiedDate // 마지막 변경 날짜
    private LocalDateTime updatedAt;

}
