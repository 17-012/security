package com.green.greengram.common;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@Component
@Getter
public class CustomFileUtils {
    private final String uploadPath;

    public CustomFileUtils(@Value("${file.dir}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    //        폴더 만들기
    public String makeFolders(String path) {
//        folder.mkdir() 제일 마지막 경로의 파일만 만든다.
//        folder.mkdirs(); 경로 중에 없는 파일은 생성하면서 파일을 만든다.
        File folder = new File(uploadPath, path);
        folder.mkdirs();
        return folder.getAbsolutePath();
    }

    //  uuid 랜덤 파일 명
    public String makeRandomFileName() {
        return UUID.randomUUID().toString();
    }

    //    랜덤 파일명 with 확장자 만들기
    public String makeRandomFileName(String fileName) {
        String filename;
        String ext = getExt(fileName);
        if (ext == null) {
            return null;
        }
        filename = makeRandomFileName() + ext;
        return filename;
    }

    public String makeRandomFileName(MultipartFile file) {
        if (file == null) {
            return null;
        }

        return makeRandomFileName(file.getOriginalFilename());
    }

    //    파일명에서 확장자 추출
    public String getExt(String fileName) {
        int idx = fileName.lastIndexOf(".");

        if (idx == -1) {
            return null;
        } else {
            return fileName.substring(idx);
        }
    }

    //파일 저장 ( target: 경로/파일명 )
    public void transferTo(MultipartFile file, String target) throws Exception {
        File saveFile = new File(uploadPath, target);
        file.transferTo(saveFile);
    }
}
