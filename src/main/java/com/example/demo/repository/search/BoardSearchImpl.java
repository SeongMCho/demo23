package com.example.demo.repository.search;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.QBoard;

import com.example.demo.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {


    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {

        QBoard board = QBoard.board; //Q도메인 객체

        JPQLQuery<Board> query = from(board);//select * from board

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.or(board.title.contains("1"));
        booleanBuilder.or(board.content.contains("1"));
//        booleanBuilder.or(board.writer.contains("1"));

        //select * from board where like title = 1 or like content = 1

        query.where(booleanBuilder);//where title like~~
        query.where(board.bno.gt(0L));

        List<Board> boardList =  query.fetch();  //실행

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board); //select * from table
        //검색 조건 과 키워드가 있다면
        if( ( types != null) && types.length > 0 && keyword != null ){
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type : types){
                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));//where title= keyword
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));//where contetn= keyword
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));//where writer= keyword
                        break;
                }
            }//for end
            query.where(booleanBuilder);
        }//if end
        query.where(board.bno.gt(0L));
        this.getQuerydsl().applyPagination(pageable, query);
        List<Board> list = query.fetch();
        long count = query.fetchCount();
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<BoardDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;    //동적쿼리를 생성하기위해 빌드로 생성된 QBoard생성
        //댓글카운터를 위해서 추가
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board); //select * from table

        query.leftJoin(reply).on(reply.board.eq(board));  //on Reply.board_bno = Board.bno

        query.groupBy(board);

        System.out.println(query);  //select board

        JPQLQuery<BoardDTO> dtoQuery = query.select(Projections.bean(BoardDTO.class,
                board.bno,board.title,board.writer,board.regDate,board.modDate,
                reply.count().as("replyCount")
                ));

        if( ( types != null) && types.length > 0 && keyword != null ){
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for(String type : types){
                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));//where title= keyword
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword));//where contetn= keyword
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));//where writer= keyword
                        break;
                }
            }//for end
            query.where(booleanBuilder);
        }//if end
        query.where(board.bno.gt(0L));

        System.out.println("검색식까지 포함한 쿼리문 : " +dtoQuery);

        this.getQuerydsl().applyPagination(pageable, dtoQuery);
        System.out.println("페이징까지 포함한 쿼리문 : " +dtoQuery);

        List<BoardDTO> list = dtoQuery.fetch();
        long count = dtoQuery.fetchCount();
//        return null;
        return new PageImpl<>(list, pageable, count);
        //검색 조건 과 키워드가 있다면

//        this.getQuerydsl().applyPagination(pageable, query);
//        List<Board> list = query.fetch();
//        long count = query.fetchCount();
//        return new PageImpl<>(list, pageable, count);


    }


}
