package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_img")
@Getter
@Setter
public class BoardImg extends BaseEntity{
    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ino;

    private String imgName;  //이미지 파일명

    private String oriImgName;  //원본 이미지 파일명

    private String imgUrl;

    private String repimgYn;  //대표이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)// 지연 로딩을 설정하여 매핑된 게시판의
                                        // 엔티티가 필요할경우 데이터를 조회하도록 함
    @JoinColumn(name = "board_bno")
    private Board board;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl){//원본, 업데이트이미자피일 , 경로
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl =imgUrl;
    }

}
