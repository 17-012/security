package com.green.greengram.common;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CustomFileUtilsTest {

    @Test
    void deleteFolder() {
        CustomFileUtils customFileUtils = new CustomFileUtils("");
        String delFolderPath = "D:/Students/BCH/file/delfolder";
//        File delFile = new File(delFolderPath);
        // 폴더 안에 파일, 폴더가 있으면 삭제 안됨
//        delFile.delete();
        // 재귀함수로 안에꺼 다 삭제하면서 올라옴
        customFileUtils.deleteFolder(delFolderPath);
    }
}