package com.example.demo.repository;

import com.example.demo.entity.Board;
import com.example.demo.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository
        extends JpaRepository<Board, Long>, BoardSearch {

//    //퍼블릭 반환타입 메소드명 (파라미터);
//    public Board findByTitle(String a);
//    public Board findAllByContent(String b);
//
//    @Query(value = "select * from Board", nativeQuery = true)
//    public List<Board> a();
//
//
//    //sql문에서 파라미터의 값을 받는 방법은 : 콜론뒤에 변수명을 적는다
//    //, nativeQuery = true붙여준다
//    @Query(value = "select b.* from Board b where b.title =:title and b.content = :content1", nativeQuery = true)
//    public Board selectOne(String title, String content1);
//
//    @Query(value = "select * from board where bno = :bno", nativeQuery = true)
//    public Board selectOne1(Long bno);

    Page<Board> findByTitleContainingOrderByBnoDesc (String keyword, Pageable pageable);

    Page<Board> findByTitleContainingOrContentContainingOrderByBnoDesc (String title, String content, Pageable pageable);


    @Query("select b from Board b where b.title like concat('%',:keyword,'%') ")
    Page<Board> thisIsSearchT(String keyword, Pageable pageable);


}
