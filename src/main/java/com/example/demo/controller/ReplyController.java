package com.example.demo.controller;


import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.dto.ReplyDTO;
import com.example.demo.entity.Board;
import com.example.demo.service.BoardService;
import com.example.demo.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;


@RestController //찾아봐야지
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/replies/")
public class ReplyController {

    private final ReplyService replyService;

    //crrud
    //등록

    @PostMapping(value = "/" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@Valid @RequestBody ReplyDTO replyDTO,
                                      BindingResult bindingResult) throws BindException {

        System.out.println("컨트롤러 파라미터로 입력받은 값 "+replyDTO); //bno 생존

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }


//        replyDTO.setBno(replyDTO.getBno());
        Long rno =  replyService.register(replyDTO);
        Map<String, Long> result = new HashMap<>();
        result.put("rno", rno);

        return result;
    }

    @GetMapping(value = "/{rno}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ReplyDTO getRead(@PathVariable("rno") Long rno){
        //입력받은 주소의 값을 파라미터로 받아서
        // service에서 읽기 기능을 수행한다 (파라미터)
        ReplyDTO replyDTO = replyService.read(rno);

        return replyDTO;

    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
                                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modify( @RequestBody ReplyDTO replyDTO){

        log.info(replyDTO);
        replyService.modify(replyDTO);  //bno있음
        Map<String, Long> result = new HashMap<>();
        result.put("rno", replyDTO.getRno());

        return result;
    }

    @DeleteMapping("/{rno}")        //www.naver.com/replies/33
    public Map<String, Long> remove(@PathVariable("rno") Long rno){
        log.info("this is delete");
        replyService.remove(rno);
        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("rno", rno);
        return resultMap;
    }
    //  /replies/list/33
    @GetMapping(value = "/list/{bno}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseDTO<ReplyDTO> getList(
            @PathVariable("bno") Long bno,
            PageRequestDTO pageRequestDTO
    ){
        log.info("넘겨받은 bno : " + bno);
        log.info("넘겨받은 pageRequestDTO" + pageRequestDTO);
        PageResponseDTO<ReplyDTO> result
           = replyService.getListOfBoard(bno, pageRequestDTO);

        return result;
    }








}
