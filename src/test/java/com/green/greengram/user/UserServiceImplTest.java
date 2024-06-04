package com.green.greengram.user;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.user.intf.UserService;
import com.green.greengram.user.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import({UserServiceImpl.class, CustomFileUtils.class})
@TestPropertySource(
        properties = {
                "file.dir=D:/Students/BCH/file/greengram_tdd/"
        }
)
class UserServiceImplTest {

    @Value("${file.dir}")
    private String uploadPath;

    @MockBean
    private UserMapper mapper;

    @Autowired
    private UserService service;

    @Autowired
    CustomFileUtils customFileUtils;

    @Test
    void postSignUp() throws Exception {
        String p1Upw = "abc";
        SignUpPostReq p1 = new SignUpPostReq();
        p1.setUserId(100);
        p1.setUpw(p1Upw);

        given(mapper.postSignUp(p1)).willReturn(1);

        String p2Upw = "def";
        SignUpPostReq p2 = new SignUpPostReq();
        p2.setUserId(200);
        p2.setUpw("def");

        given(mapper.postSignUp(p2)).willReturn(2);

        MultipartFile fm1 = new MockMultipartFile(
                "pic", "64440c58-08ac-41ca-89e3-32f8f9b7b059.jpg", "image/jpg",
                new FileInputStream("D:/Students/BCH/file/greengram_tdd/user/8/64440c58-08ac-41ca-89e3-32f8f9b7b059.jpg")

        );

        int result1 = service.postSignUp(p1, fm1);
        assertEquals(1, result1);

        File savedFile1 = new File(uploadPath
                , String.format("%s/%d/%s", "user", p1.getUserId(), p1.getPic()));
        assertTrue(savedFile1.exists(), "1. 파일이 만들어지지 않음");
        savedFile1.delete();

        assertNotEquals(p1Upw, p1.getUpw());


        int result2 = service.postSignUp(p2, fm1);
        assertEquals(2, result2);

        File savedFile2 = new File(uploadPath
                , String.format("%s/%d/%s", "user", p2.getUserId(), p2.getPic()));
        assertTrue(savedFile2.exists(), "2. 파일이 만들어지지 않음");
        savedFile2.delete();

        assertNotEquals(p2Upw, p2.getUpw());

    }

    @Test
    void postSignIn() {
        SignInPostReq req1 = new SignInPostReq();
        req1.setUid("id1");
        req1.setUpw("1212");
        String hashedUpw1 = BCrypt.hashpw(req1.getUpw(), BCrypt.gensalt());

        SignInPostReq req2 = new SignInPostReq();
        req2.setUid("id2");
        req2.setUpw("2323");
        String hashedUpw2 = BCrypt.hashpw(req2.getUpw(), BCrypt.gensalt());

        User user1 = new User(10, req1.getUid(), hashedUpw1, "홍길동1", "사진1.jpg", null, null);
        given(mapper.postSignIn(req1.getUid())).willReturn(user1);

        User user2 = new User(20, req2.getUid(), hashedUpw2, "홍길동2", "사진2.jpg", null, null);
        given(mapper.postSignIn(req2.getUid())).willReturn(user2);


        try (MockedStatic<BCrypt> mockedStatic = mockStatic(BCrypt.class)) {

            mockedStatic.when(() -> BCrypt.checkpw(req1.getUpw(), user1.getUpw())).thenReturn(true);
            mockedStatic.when(() -> BCrypt.checkpw(req2.getUpw(), user2.getUpw())).thenReturn(true);

            SignInRes res1 = service.postSignIn(req1);
            assertEquals(user1.getUserId(), res1.getUserId(), "1. userId 다름");
            assertEquals(user1.getNm(), res1.getNm(), "1. nm 다름");
            assertEquals(user1.getPic(), res1.getPic(), "1. pic 다름");

            mockedStatic.verify(() -> BCrypt.checkpw(req1.getUpw(), user1.getUpw()));

            SignInRes res2 = service.postSignIn(req2);
            assertEquals(user2.getUserId(), res2.getUserId(), "2. userId 다름");
            assertEquals(user2.getNm(), res2.getNm(), "2. nm 다름");
            assertEquals(user2.getPic(), res2.getPic(), "2. pic 다름");

            mockedStatic.verify(() -> BCrypt.checkpw(req2.getUpw(), user2.getUpw()));
        }

        SignInPostReq req3 = new SignInPostReq();
        req3.setUid("id3");
        given(mapper.postSignIn(req3.getUid())).willReturn(null);

        Throwable ex1 = assertThrows(RuntimeException.class, () -> {
            service.postSignIn(req3);
        }, "아이디 없음 예외 처리 안 함");
        assertEquals("아이디를 확인해주세요.", ex1.getMessage(), "아이디 없음 에러 메시지 다름");

        SignInPostReq req4 = new SignInPostReq();
        req4.setUid("id4");
        req4.setUpw("6666");

        String hashedUpw4 = BCrypt.hashpw("7777", BCrypt.gensalt());
        User user4 = new User(100, req4.getUid(), hashedUpw4, "홍길동4", "사진4.jpg", null, null);

        given(mapper.postSignIn(user4.getUid())).willReturn(user4);
        Throwable ex2 = assertThrows(RuntimeException.class, () -> {
            service.postSignIn(req4);
        }, "비밀번호 다름 예외 처리 안 함");
//        assertEquals("비밀번호를 확인해주세요.", ex2.getMessage(), "비밀번호 다름, 에러 메시지 다름");

    }

    @Test
    void getUserInfo() {
        UserInfoGetReq p1 = new UserInfoGetReq(1, 2);
        UserInfoGetRes result1 = new UserInfoGetRes();
        result1.setNm("test1");
        given(mapper.selProfileUserInfo(p1)).willReturn(result1);

        UserInfoGetReq p2 = new UserInfoGetReq(3, 4);
        UserInfoGetRes result2 = new UserInfoGetRes();
        result2.setNm("test2");
        given(mapper.selProfileUserInfo(p2)).willReturn(result2);

        UserInfoGetRes res1 = mapper.selProfileUserInfo(p1);
        assertEquals(result1, res1);
        UserInfoGetRes res2 = mapper.selProfileUserInfo(p2);
        assertEquals(result2, res2);
    }

    @Test
    void patchProfilePic() throws Exception {
        UserProfilePatchReq p1 = new UserProfilePatchReq();
        int signedUserId1 = 500;
        p1.setSignedUserId(signedUserId1);
        String midPath1 = String.format("%s/user/%d", uploadPath, p1.getSignedUserId());
        File dic1 = new File(midPath1, "a.jpg");
        final String ORIGIN_FILE_PATH = String.format("%s/test/%s", uploadPath, "a.jpg");
        File originFile = new File(ORIGIN_FILE_PATH);
        File copyFile1 = new File(midPath1, "a.jpg");

        customFileUtils.deleteFolder(midPath1);
        customFileUtils.makeFolders("user/" + signedUserId1);

        Files.copy(originFile.toPath(), copyFile1.toPath(), StandardCopyOption.REPLACE_EXISTING);

        p1.setSignedUserId(signedUserId1);
        MultipartFile fm1 = new MockMultipartFile(
                "pic", "b.jpg", "image/jpg",
                new FileInputStream(String.format("%s/test/b.jpg",uploadPath))
        );
        p1.setPic(fm1);
        String fileNm1 = service.patchProfilePic(p1);
        assertEquals(fileNm1,p1.getPicName());

//        // 원본 파일 경로
//        Path sourcePath = Paths.get(uploadPath,"/test/a.jpg");
//        // 복사할 파일 경로
//        Path destinationPath = Paths.get(midPath1+"/"+fileNm1);
//        if(!dic1.exists()){
//            dic1.mkdirs();
//            try {
//                // 파일 복사
//                Files.copy(sourcePath, destinationPath);
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        else {
//            File[] fileList = dic1.listFiles();
//            if (fileList.length == 0) {
//                try {
//                    Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
//                } catch (IOException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//
//        }
    }
}