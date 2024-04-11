package com.example.demo.dto;

import com.example.demo.entity.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDTO {

    private Long rno;

    @NotNull
    private Long bno;

    @NotBlank
    private String replyText; //댓글 내용

    @NotBlank
    private String replyer;

    private LocalDateTime regDate;  //입력시에 용도 출력시 읽기 목록
    private LocalDateTime modDate;
}
