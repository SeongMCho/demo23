package com.example.demo.controller;


import com.example.demo.dto.BoardDTO;
import com.example.demo.dto.PageRequestDTO;
import com.example.demo.dto.PageResponseDTO;
import com.example.demo.entity.Board;
import com.example.demo.service.BoardService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.crypto.interfaces.PBEKey;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/board/")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Value("${project.uploadpath}")
    private String uploadpath;


    //날짜별로 폴더생성
    public String makeDir() {

        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String now=sdf.format(date);
        //String absolutePath = new File("").getAbsolutePath() + "\\";
        String path= uploadpath + "\\" +now; //경로
        File file = new File(path);
        System.out.println(file);
        if(file.exists()==false) {//파일이 존재하면 true
            file.mkdir(); //폴더생성
        }

        return path;
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        //최초진입시에는 page디폴트 1
        PageResponseDTO<BoardDTO> responseDTO
                = boardService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);

    }

    @GetMapping("/register")
    public void registerGET(){

    }

    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList)
    throws Exception {



        log.info("board Post register...............");
        //만약에 에러가 발생한다면~~
        if(bindingResult.hasErrors()){
            log.info("has errorsssssssssssssss");

            redirectAttributes
                    .addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/board/register";
        }

        log.info(boardDTO);
        Long bno = boardService.register(boardDTO, boardImgFileList);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/board/list"; //등록 성공후에 list이동

    }

    @GetMapping({"/read", "/modify"})   //사용하는 내용이 같기에 url만 배열로 지정합니다.
    public void read(Long bno,
                     PageRequestDTO pageRequestDTO
                , Model model){

       BoardDTO boardDTO = boardService.readOne(bno);

       log.info(boardDTO);

       model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/modify")
    public String modify(@Valid BoardDTO boardDTO,
                         PageRequestDTO pageRequestDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){

        log.info("board modify post................" + boardDTO);

        if(bindingResult.hasErrors()){
            log.info("has errorsssssssssssssss");

            String link = pageRequestDTO.getLink();

            redirectAttributes
                    .addFlashAttribute("errors", bindingResult.getAllErrors());
///////////////////////////////////////////////////////
            redirectAttributes.addAttribute("bno", boardDTO.getBno());
            ////////////////////////////////////////////////////////
            return "redirect:/board/modify?"+link;
        }

        boardService.modify(boardDTO); //수정

        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/read";

    }


    @PostMapping("remove")
    public String remove(Long bno,
         RedirectAttributes redirectAttributes){

        log.info("remove post...." + bno);

        boardService.remove(bno);

        redirectAttributes
          .addFlashAttribute("result", "removed");

        return "redirect:/board/list";

    }

    @GetMapping("/file")
    public void uploadGet(){

    }
    @PostMapping("/file")
    public String upload(@RequestParam("file")MultipartFile file){

        String origin=file.getOriginalFilename();//파일명
        System.out.println("오리지널 파일 이름 :" + origin);
        long size=file.getSize();//사이즈
        System.out.println("파일크기 : " + size);
        String type=file.getContentType();//파일데이터의 컨텐츠타입
        //System.out.println("파일의 컨텐츠 타입" + type);
        try {
            byte[] arr=file.getBytes();



        } catch (IOException e) {
            e.printStackTrace();
        }

        String filename=origin.substring(origin.lastIndexOf("\\")+1);
        System.out.println(filename);

        String filepath = makeDir();
        //브라우저별로 경로가 포함되서 올라오는 경우가 있기에 간단한 처리.
        System.out.println("경로 : " + filepath);

        String uuid = UUID.randomUUID().toString();

        String savename = filepath + "\\" +uuid + "_" + filename;

        System.out.println(savename);

        File save = new File(savename);

        try{
            file.transferTo(save);
        }catch (Exception e){

        }

        return "redirect:/board/file";
    }




}
