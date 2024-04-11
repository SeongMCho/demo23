package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@ToString
public class BaseEntity {
    @CreatedDate    //테이블에 값을 입력할때 사용
    @Column(name = "regDate", updatable = false) //업데이트시에는 false
    private LocalDateTime regDate;

    @LastModifiedDate   //수정할때마다
    @Column(name = "moddate") //컬럼의 이름
    private LocalDateTime modDate;
}
