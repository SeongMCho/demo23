package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity //엔티티임을 명시함 // 테이블을 만들수 있게 됩니다.
@Getter
@Setter
@Builder //빌더패턴
@AllArgsConstructor //모든생성자
@NoArgsConstructor //기본생성자
@ToString 
public class Board extends BaseEntity {//공통기능을 수행하는 엔티티를 상속

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincreament
    private Long bno;

    @Column(length = 500, nullable = false) // null 허용 여부
    private String title;

    @Column(length = 2000, nullable = false) // null 허용 여부
    private String content;

    @Column(length = 500, nullable = false) // null 허용 여부
    private String writer;



}
