package com.example.demo.repositoryTest;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Membership;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
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
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository; //주입받는다

    @Autowired
    private MemberRepository memberRepository;
    //등록 / 수정 / 삭제 / 상세보기 / 목록
    
    @Test
    public void insertTest(){
        //입력객체 생성
        //Board board = new Board();
        //빌더패턴 Board.builder()
        // .set이였던걸그냥씀.set.set.build();
        for(int i =0; i< 500; i++){
        Membership board = Membership.builder()
                .userId("1."+ i )
                .pass("21" + 1)
                .name("이름." + 1)
                .build();
        //boardRepository의 메소드 save(엔티티); 호출
        // 저장 //insert into (bno, title~~) values ('bno'~)
            Membership result = memberRepository.save(board);
        }
    }

    @Test
    public void modify111111(){

        //수정할 객체를 생성합니다.빌더패턴
        //수정을 위해서 bno는 필요합니다.
        //update table set
        // aaa = 'aaa', 로변경되고
        // bbb = 'bbb'
        // where bno = 몇번? 여기서 
        Board board = Board.builder()
                .bno(3L)
                .title("수정한 제목")
                .content("수정한 내용")
                .writer("수정한 작성자")
                .build();
        //생성한 객체를 save를 호출하여 파라미터로 전달한다.
        boardRepository.save(board);

    }
    @Test
    public void del(){
        boardRepository.deleteById(1L);
    }

    @Test
    public void read(){
        Long bno = 2L;
        //select 읽기는
        //반환타입을 Optional<엔티티> 로 받습니다.
        //find=select / select from Board where(by) id = (bno)
        Optional<Board> board = boardRepository.findById(bno);
        log.info(board);
    }
//    @Test
//    public void readTitleTest(){
//        String str = "제목입니다."; //아이디select처럼 특정조건을 조회
//        //findByTitle = select  from board where(by) title = (str)
//        //Optional<Board> board = boardRepository.findByTitle(str);
//        Board board = boardRepository.findByTitle(str);
////        Board b =  board.orElseThrow();
//
//        log.info(board.getRegDate());
//    }
//    @Test
//    public void readContetTest(){
//        String content = "빌더패턴을 사용한 내용입니다.";
//        //Optional<Board> board = boardRepository.findAllByContent(content);
//        Board board = boardRepository.findAllByContent(content);
//        //Board board1 = board.orElseThrow();
//        log.info(board.getModDate());
//    }
//    @Test
//    public void a(){
//        List<Board> board = boardRepository.a();
//        log.info(board.get(0).getModDate());
//        System.out.println(board.get(0));
//    }

//    @Test
//    public  void selectOneTest(){
//        Board board = boardRepository.selectOne("제목입니다.", "빌더패턴을 사용한 내용입니다.");
//
//        log.info(board);
//    }
//
//    @Test
//    public void sss (){
//        Board board = boardRepository.selectOne1(3L);
//        log.info(board);
//    }
    
    @Test
    public void testPaging(){
        
        //1페이지를 보고 싶고, 정렬은 bno를 기준
        //pageable은 domain꺼
        Pageable pageable = PageRequest
                .of(1, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        log.info( "total count 총갯수 :" + result.getTotalElements() ); //검색해오는 총 게시물수
        log.info( "총 페이지수 : " + result.getTotalPages()); // 총페이지수
        log.info( " 현재페이지 : " + result.getNumber() );   //현재페이지
        log.info( " 페이지 크기  page size : " + result.getSize()); //10될 예정

        log.info( "Page<Board> 타입에서 getContent()는 컬렉션에 있는 타입의 List<Board>를 가져옵니다.");

        List<Board> boardList =  result.getContent();
        boardList.forEach(board -> log.info(board));
        
    }

    @Test
    public void insertTestfor(){
        for(int i = 0; i< 500; i++){
            Board board = new Board();
            board.setTitle("제목입니다." + i);
            board.setContent("내용입니다." + i);
            board.setWriter("유저" + i);
            boardRepository.save(board);
        }
    }

    @Test
    public void searchTitlePageable(){

Pageable pageable =
        PageRequest
                .of(35,5, Sort.by("bno")
                        .descending());

        Page<Board> boardPage =
                boardRepository
                        .findByTitleContainingOrderByBnoDesc("1", pageable);

        log.info("총게시물수" + boardPage.getTotalElements());
        log.info("총페이지수" + boardPage.getTotalPages());
        log.info("현재페이지" + boardPage.getNumber());
        log.info("페이지 사이즈 " + boardPage.getSize());

        List<Board> boardList = boardPage.getContent(); //페이지어블을 적용한 데이터
        boardList.forEach(board -> log.info(board));


    }
    @Test
    public void serchTorC(){

        Pageable pageable =
                PageRequest
                        .of(0,10, Sort.by("bno")
                                .descending());

        String keyword = "1";
        Page<Board> boardPage= boardRepository
                .findByTitleContainingOrContentContainingOrderByBnoDesc(keyword, keyword, pageable);

//        boardPage.getContent().forEach(board -> log.info(board));
        List<Board> boardList = boardPage.getContent();
        boardList.forEach(board -> log.info(board));

    }

    @Test
    public void querySearch(){
        Pageable pageable =
                PageRequest
                        .of(0,10, Sort.by("bno")
                                .descending());

        Page<Board> boardPage = boardRepository
                .thisIsSearchT("3", pageable);

        boardPage.getContent().forEach(board -> log.info(board));

    }

    @Test
    public void search1Test(){

        //2페이지 검색 order by bno desc

        Pageable pageable =
                PageRequest
                        .of(2, 10,Sort.by("bno")
                        .descending());


        boardRepository.search1(pageable);
    }

    @Test
    public void testSearchAll(){

        String[] types = { "t", "c", "w"};
        String keyword = "내용";
        //페이징처리
        Pageable pageable = PageRequest
                .of(0, 30, Sort.by("bno").descending());

        Page<Board> result = boardRepository.searchAll(types, keyword, pageable);



        result.getContent().forEach(board -> log.info(board));




    }

    @Test
    public void testSearchAll1(){

        String[] types = { "t", "c"};
        String keyword = "1";
        //페이징처리
        Pageable pageable = PageRequest
                .of(0, 30, Sort.by("mno").descending());

        Page<BoardDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);



        result.getContent().forEach(board -> log.info(board));




    }


    @Test
    public void searctWRC(){
        String[] types = { "t", "c"};
        String keyword = "493";
        //페이징처리
        Pageable pageable = PageRequest
                .of(0, 30, Sort.by("bno").descending());

        Page<BoardDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);



        result.getContent().forEach(board -> log.info(board));
    }











}
