package com.example.demo.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@Log4j2
public class FileService {

    public String uploadFile(String uploadpath, String originalFileName, byte[] fileDate)throws Exception{

        UUID uuid = UUID.randomUUID();
        //서로 다른 개체들을 구별하기위해서 이름을 부여할때 사용, 실제 사용시 중복될 가능성이 거의 없기에
        //파일의 이름으로 사용해서 중복문제를 해결

        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadpath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl); 
        //파일생성
        fos.write(fileDate);
        fos.close();
        //파일이름 반환
        return savedFileName;

    }

    public void deleteFile(String filePath) throws Exception{

        File deleteFile = new File(filePath);
        //파일이 존재한다면
        if(deleteFile.exists()){
            deleteFile.delete();
            log.info("파일 삭제됨" + filePath);
        }else {
            log.info("파일이 존재하지 않습니다." + filePath);
        }

    }
}
