package com.example.demo.repository.search;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {

    Page<Board> search1(Pageable pageable);//연습//쿼리문보기

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    Page<BoardDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);


}
