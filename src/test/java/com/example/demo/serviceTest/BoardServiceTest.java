package com.example.demo.serviceTest;

import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import com.example.demo.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class BoardServiceTest {
    @Autowired
    private BoardService boardService; //주입받는다

    @Test
    public void registerTest(){

        BoardDTO boardDTO = BoardDTO.builder()
                .title("20240304")
                .content("content20240304")
                .writer("신짱구")
                .build();

        Long bno = boardService.register(boardDTO);

        if(bno != 0){
            log.info("저장되었습니다. 번호는 : " + bno);
        }else {
            System.out.println("저장실패");
        }
    }

    @Test
    public void readOneTest(){
        Long bno = 500L; //가지고 있는 bno값
        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);
    }

    @Test
    public void modifyTest(){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(500L) //있는값
                .title("수정하는값 03041445")
                .content("수정내용")
                .writer("홍길동")
                .build();

        boardService.modify(boardDTO);

        log.info(boardService.readOne(boardDTO.getBno()));

    }

    @Test
    public void del(){
        Long bno = 500L;

        boardService.remove(bno);

    }

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("twc")
                .keyword("1")
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);


    }


}
