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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import({UserServiceImpl.class})
@TestPropertySource(
        properties = {
                "file.dir=D:/Students/BCH/file/greengram_tdd/"
        }
)
class UserService2ImplTest {

    @Value("${file.dir}")
    private String uploadPath;

    @MockBean
    private UserMapper mapper;

    @Autowired
    private UserService service;

    @MockBean
    CustomFileUtils customFileUtils;

    @Test
    void patchProfilePic() throws Exception {
        long signedUserId1 = 500;
        UserProfilePatchReq p1 = new UserProfilePatchReq();
        p1.setSignedUserId(signedUserId1);
        MultipartFile fm1 = new MockMultipartFile(
                "pic", "b.jpg", "image/jpg",
                new FileInputStream(String.format("%s/test/b.jpg",uploadPath))
        );
        p1.setPic(fm1);
        given(customFileUtils.makeRandomFileName(fm1)).willReturn("a1b2.jpg");

        String fileNm1 = service.patchProfilePic(p1);

        verify(mapper).updProfilePic(p1);
        String midpath = String.format("user/%d", p1.getSignedUserId());

        verify(customFileUtils).deleteFolder(midpath);
        verify(customFileUtils).makeFolders(midpath);
        verify(customFileUtils).transferTo(p1.getPic(), String.format("%s/%s",midpath, fileNm1));
    }
}