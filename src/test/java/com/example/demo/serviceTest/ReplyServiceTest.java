package com.example.demo.serviceTest;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.entity.Board;
import com.example.demo.service.BoardService;
import com.example.demo.service.ReplyService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyServiceTest {
    @Autowired
    private ReplyService replyService; //주입받는다

    @Test
    public void registerTest(){
        //ReplyDTO를 위한 Board
        Board board = new Board();
        board.setBno(496L); //만들어준 bno만있는 객체를
        //아래 넣어준다

        ReplyDTO replyDTO = ReplyDTO.builder()
                //.rno() 오토인크리먼트라서 안해도됨
                .bno(board.getBno())  //board를 입력해야해서 만들어야함
                .replyText("11111")
                .replyer("신짱구")
                .build();

        Long rnoaaaaa = replyService.register(replyDTO);

        if(rnoaaaaa != null){
            log.info("저장되었습니다. 번호는 : " + rnoaaaaa);
        }else {
            System.out.println("저장실패");
        }



//
//        BoardDTO boardDTO = BoardDTO.builder()
//                .title("20240304")
//                .content("content20240304")
//                .writer("신짱구")
//                .build();
//
//        Long bno = boardService.register(boardDTO);
//
//        if(bno != 0){
//            log.info("저장되었습니다. 번호는 : " + bno);
//        }else {
//            System.out.println("저장실패");
//        }
    }

    @Test
    public void read (){

      ReplyDTO replyDTO =  replyService.read(999L);

      log.info(replyDTO);
    }
    @Test
    public void modifyTest(){
        Board board = new Board();
        board.setBno(497L);

        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setRno(1006L);
        replyDTO.setReplyText("123123123");
        replyDTO.setBno(board.getBno());

        replyService.modify(replyDTO);
    }

    @Test
    public void removeTest(){

        replyService.remove(999L);


    }
    @Test
    public void getListOfBoardTest(){

        Long bno = 497L;
        //웹 -> 컨트롤러에서 입력받은 객체
        PageRequestDTO pageRequestDTO =
                new PageRequestDTO();
        pageRequestDTO.setSize(10);
        pageRequestDTO.setPage(2);

        PageResponseDTO<ReplyDTO> a =
                replyService.getListOfBoard(bno, pageRequestDTO);

        a.getDtoList().forEach(replyDTO -> log.info(replyDTO));





    }




}
