package com.green.greengram.user.intf;

import com.green.greengram.user.model.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    int postSignUp(SignUpPostReq p, MultipartFile pic);
    SignInRes postSignIn(SignInPostReq p);
    UserInfoGetRes getUserInfo(UserInfoGetReq p);
    String patchProfilePic(UserProfilePatchReq p);
}
