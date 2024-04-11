package com.example.demo.service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {

    //등록
    public Long register(BoardDTO boardDTO);

    public Long register(BoardDTO boardDTO, List<MultipartFile> boardImgFileList)  throws Exception;
    //조회 읽기 리드 read seletOne findbyId
    public BoardDTO readOne(Long bno);
    //수정
    public void modify(BoardDTO boardDTO);

    //삭제
    public void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

}
