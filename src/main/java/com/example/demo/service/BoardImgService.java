package com.example.demo.service;


import com.example.demo.entity.BoardImg;
import com.example.demo.repository.BoardImgRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardImgService {


    @Value("${project.uploadpath}")
    private String boardImgPath;

    private final BoardImgRepository boardImgRepository;

    private final FileService fileService;

    public void saveBoardImg(BoardImg boardImg, MultipartFile file)throws Exception{
        String oriImgName = file.getOriginalFilename();
        System.out.println(oriImgName);
        System.out.println(oriImgName);
        System.out.println(oriImgName);
        System.out.println(oriImgName);
        String imgName = "";
        String imgUrl = "";
        
        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(boardImgPath, oriImgName, file.getBytes());
            imgUrl = boardImgPath+"/"+imgName;
            boardImg.updateItemImg(oriImgName, imgName, imgUrl);
            boardImgRepository.save(boardImg);
        }

        
    }

}
