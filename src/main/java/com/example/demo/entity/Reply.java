package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "Reply",
        indexes = {@Index(name = "idx_reply_board_bno",
                columnList = "board_bno")})
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class Reply extends BaseEntity { //테이블 이름

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;//테이블의 pk

   //지금나 to 상대방
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private String replyText; //댓글 내용
    private String replyer;     //댓글 작성자

    //기능 수정을 위한 기능 //세터랑 같다
    public void chageText(String replyText){
        this.replyText = replyText;
    }

}
