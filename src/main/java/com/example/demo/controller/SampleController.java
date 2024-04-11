package com.example.demo.controller;

import com.example.demo.dto.BoardDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/abc/")
public class SampleController {

    // /abc/a               //받는타입         보내는 타입

    @PostMapping(value = "/a",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String aaa(String str){

        System.out.println(str);

        String str1 = "안녕하세요";

        return str1;
    }

    @PostMapping(value = "/a/{rno}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String bbb(@PathVariable("rno") String str,
                    @RequestBody  BoardDTO boardDTO  ){

        System.out.println(str);
        System.out.println(boardDTO);

        String str1 = "안녕하세요";

        return str1;
    }


}
