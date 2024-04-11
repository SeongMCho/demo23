package com.example.demo.dto;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    //유효성 검사
    private Long bno;

    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String writer;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
    
    private Long replyCount;  // 댓글숫자

    private List<BoardImgDTO> boardImgDTOList = new ArrayList<>();

    private List<Long> boardImgIds = new ArrayList<>();




}
