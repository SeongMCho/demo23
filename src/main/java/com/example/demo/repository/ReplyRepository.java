package com.example.demo.repository;

import com.example.demo.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
                                            // <엔티티,엔티티id타입>

    //Reply 테이블에 조건은 bno = 입력받는 bno와 같은거
    // 특정게시물에 댓글
    @Query("select r from Reply r where r.board.bno = :bno")
    Page<Reply> listOfBoard(Long bno, Pageable pageable);



}
