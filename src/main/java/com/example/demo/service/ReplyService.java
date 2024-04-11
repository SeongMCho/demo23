package com.example.demo.service;

import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.dto.ReplyDTO;

public interface ReplyService {

    //등록 //DTO(웹) <> Entity(db)
    //ReplyDTO 는 사용자에게 form 을 통해 입력받은
    //rno board replyText replyer 등을 컨트롤러에서
    //파라미터 자동수집을 통해 ReplyDTO에 자동으로 수집된다
    //Long rno , String replyText 그렇게 입력받은 dto를
    //입력해준다
    public Long register(ReplyDTO replyDTO);
    //읽기  read
    public ReplyDTO read(Long rno);  //repository.findById
    //수정 modify
    void modify(ReplyDTO replyDTO);
    //삭제 remove
    void remove(Long rno);

    //목록
    PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO);
    //반환타입 <컬렉션 ReplyDTO> 메소드명 (파라미터로는 bno 어떤게시물에 댓글이 무엇인가, PageRequestDTO 사용자가 보고있는 
    //page size 현재 페이지 그리고 총 사이즈 등을 통해서 페이징처리
}
