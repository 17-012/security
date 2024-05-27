package com.green.greengram.user;

import com.green.greengram.user.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

@Mapper
public interface UserMapper {
    int postSignUp(SignUpPostReq p);
    User postSignIn(SignInPostReq p);
    UserInfoGetRes selProfileUserInfo(UserInfoGetReq p);

    void updProfilePic(UserProfilePatchReq p);
}
