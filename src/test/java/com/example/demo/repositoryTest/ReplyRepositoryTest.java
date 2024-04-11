package com.example.demo.repositoryTest;


import com.example.demo.entity.Board;
import com.example.demo.entity.Reply;
import com.example.demo.repository.ReplyRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testInsert(){
        //db에 있는 값
        Long bno = 497L;

        Board board = Board.builder().bno(bno).build();

        for(int i = 0; i<500; i++){
            Reply reply = Reply.builder()
                    .board(board)
                    .replyText("댓글" +i)
                    .replyer("replyer1"+i)
                    .build();

            Reply a =  replyRepository.save(reply);

        }

    }

    @Test
    public void testBoardReplies(){

        Long bno = 497L;   //댓글이 있는 게시물

        //Pageable domain 꺼    // PageRequest.of(현재페이지, 한페이지사이즈, Sort.by("정렬할기준값").descending()); 내림차순
        Pageable pageable = PageRequest
                .of(0, 10, Sort.by("rno")
                        .descending());

        Page<Reply> replyPage = replyRepository.listOfBoard(bno, pageable);
        log.info(replyPage); //무엇이있는지 확인
        replyPage.getContent().forEach(reply -> log.info(reply));


    }

    @Test
    public void testUpdate(){
        Long bno = 497L; Long rno = 1001L;
        Board board = Board.builder().bno(bno).build();
        Reply reply = Reply.builder().rno(rno).board(board).replyText("수정").replyer("수정자").build();
        replyRepository.save(reply);
    }
    @Test
    public void readTest(){

        Long rno = 1001L;
        Optional<Reply> replyOpt = replyRepository.findById(rno);
        Reply reply = replyOpt.orElseThrow();

        log.info(reply); log.info("등록날짜" + reply.getRegDate());

    }
    @Test
    public void remove(){
        Long rno = 1000L;

        replyRepository.deleteById(rno);
        readTest();

    }










}
