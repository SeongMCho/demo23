package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Membership extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincreament
    private Long mno;

    @Column(length = 60, nullable = false) // null 허용 여부
    private String userId;

    @Column(length = 60, nullable = false) // null 허용 여부

    private String pass;
    @Column(length = 30, nullable = false) // null 허용 여부
    private String name;

}
