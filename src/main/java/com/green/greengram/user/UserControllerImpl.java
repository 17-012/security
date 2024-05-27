package com.green.greengram.user;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.user.model.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("api/user")
@RequiredArgsConstructor
@Tag(name = "유저 CRUD", description = "유저 관련 클래스")
public class UserControllerImpl {
    private final UserServiceImpl service;

    @PostMapping("sign-up")
    public ResultDto<Integer> postSignUp (@RequestPart SignUpPostReq p,
                                          @RequestPart(required = false) MultipartFile pic){

        int result = service.postSignUp(p,pic);

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(result == 1 ? "회원가입 성공" : "회원가입 실패")
                .resultData(result)
                .build();
    }

    @PostMapping("sign-in")
    public ResultDto<SignInRes> postSignIn(@RequestBody SignInPostReq p){
        SignInRes result = service.postSignIn(p);

        return ResultDto.<SignInRes>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("로그인 성공")
                .resultData(result)
                .build();
    }

    @GetMapping
    public ResultDto<UserInfoGetRes> getUserInfo(@ParameterObject @ModelAttribute UserInfoGetReq p){
        UserInfoGetRes result = service.getUserInfo(p);

        return ResultDto.<UserInfoGetRes>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("유저 프로필")
                .resultData(result)
                .build();
    }

    @PatchMapping(value = "pic", consumes = "multipart/form-data")
    public ResultDto<String> patchProfilePic(@ModelAttribute UserProfilePatchReq p){
        String result = service.patchProfilePic(p);

        return ResultDto.<String>builder()
                        .statusCode(HttpStatus.OK)
                        .resultMsg("사진 변경 완료")
                        .resultData(result)
                        .build();
    }
}
