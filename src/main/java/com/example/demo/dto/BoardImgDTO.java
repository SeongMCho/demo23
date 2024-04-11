package com.example.demo.dto;

import com.example.demo.entity.BoardImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardImgDTO {

        private Long ino;

        private String imgName;  //이미지 파일명

        private String oriImgName;  //원본 이미지 파일명

        private String imgUrl;

        private String repimgYn;  //대표이미지 여부

        private LocalDateTime regDate;  //생성일시
        private LocalDateTime modDate;  //수정일시

        private static ModelMapper modelMapper = new ModelMapper();

        public static BoardImgDTO of(BoardImg boardImg){
                return modelMapper.map(boardImg, BoardImgDTO.class);
        }


}
