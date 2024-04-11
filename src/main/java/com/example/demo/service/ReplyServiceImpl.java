package com.example.demo.service;

import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Reply;
import com.example.demo.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service    //여기는 서비스영역임을 명시함
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository; //지금까지만들어놓은 기능 사용가능
    private final ModelMapper modelMapper;


    @Override
    public Long register(ReplyDTO replyDTO) {
        //입력받은 DTO를 replyRepository에 있는 입력에 해당하는 save()를
        // 사용하기위해서
        //save(파라미터로 Entity를 입력받는데 replyRepository를 만들때
        // 컬렉션에 Reply를 사용했기에)
        // 타입이 Reply인 객체를 넣어야함 그래서 ReplyDTO를 Reply로 변환
        //  변환할타입 reply = modelMapper.map(변환을위해서입력받은객체, 변환할타입.class);
        log.info("변환될 파라미터로 입력받은 replyDTO" + replyDTO); //bno ok
        Reply reply = modelMapper.map(replyDTO, Reply.class); //bno no
        Board board = new Board();
        board.setBno(replyDTO.getBno());  //bno 생성
        reply.setBoard(board); //bno ok
        log.info("변환된 reply" + reply);
        //Reply reply1 = new Reply(replyDTO.getRno(),
        // replyDTO.getBoard(), replyDTO.getReplyText(),replyDTO.getReplyer());
        //기능수행
        //Reply result =  replyRepository.save(reply);
        //Long bno = result.getRno();
        Long rno =  replyRepository.save(reply).getRno();

        return rno;
    }

    @Override
    public ReplyDTO read(Long rno) {

        Optional<Reply> replyOpt = replyRepository.findById(rno);
        Reply reply = replyOpt.orElseThrow();

        //Entity -> DTO
        //변환된 타입 변수 = modelMapper.map(변환될 객체, 변환된.class)
        System.out.println("변환전" + reply);
        ReplyDTO replyDTO = modelMapper.map(reply, ReplyDTO.class);
        replyDTO.setBno(reply.getBoard().getBno());
        System.out.println("변환후" + replyDTO);
        return replyDTO;
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        log.info("service에 들어온 replyDTO" + replyDTO); //bno가 있다
        
        Reply reply= modelMapper.map(replyDTO, Reply.class);//변환으로
        //매핑될때 bno가 입력이 되질 않는다
        //직접 넣어준다.
        reply.setBoard(Board.builder().bno(replyDTO.getBno()).build());//bno추가
        System.out.println(reply+ " bno:"+reply.getBoard().getBno());

        replyRepository.save(reply);

    }

    @Override
    public void remove(Long rno) {
        //기능 삭제
        replyRepository.deleteById(rno);

    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {


        //페이징 처리를 위해서 pageable 만든다 설정한다 그리고 파라미터에 넣는다
        Pageable pageable = PageRequest.
                of(pageRequestDTO.getPage()-1, pageRequestDTO.getSize(), Sort.by("rno")
                        .descending());
        Page<Reply> replyPage =  replyRepository.listOfBoard(bno, pageable);//

        //dtoList를 사용하기 위해서 entity > dto로변환
        List<Reply> replyList = replyPage.getContent();
        List<ReplyDTO> dtoList
                = replyList.stream().map(reply -> modelMapper.map(reply, ReplyDTO.class))
                .collect(Collectors.toList());

        //PageRequestDTO pageRequestDTO, List<E> dtoList, int total
        PageResponseDTO<ReplyDTO> pageResponseDTO
                = new PageResponseDTO<>(pageRequestDTO, dtoList, (int) replyPage
                .getTotalElements());


        return pageResponseDTO;
    }
}
