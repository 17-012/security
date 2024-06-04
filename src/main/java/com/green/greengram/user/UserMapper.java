package com.green.greengram.user;

import com.green.greengram.user.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface UserMapper {
    int postSignUp(SignUpPostReq p);
    User postSignIn(String uid);
    UserInfoGetRes selProfileUserInfo(UserInfoGetReq p);

    void updProfilePic(UserProfilePatchReq p);


    List<User> selTest(long userId);
}
