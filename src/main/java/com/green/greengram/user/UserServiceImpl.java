package com.green.greengram.user;

import com.green.greengram.common.CookieUtils;
import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.security.AuthenticationFacade;
import com.green.greengram.security.JwtTokenProviderV2;
import com.green.greengram.security.MyUser;
import com.green.greengram.security.MyUserDetails;
import com.green.greengram.user.intf.UserService;
import com.green.greengram.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProviderV2 tokenProvider;
    private final CookieUtils cookieUtils;
    private final AuthenticationFacade authenticationFacade;


    @Transactional
    public int postSignUp(SignUpPostReq p, MultipartFile pic) {
        String saveFileName = customFileUtils.makeRandomFileName(pic);
        p.setPic(saveFileName);

        String password = passwordEncoder.encode(p.getUpw());
        p.setUpw(password);
//        p.setUpw(BCrypt.hashpw(p.getUpw(), BCrypt.gensalt()));
        int result = mapper.postSignUp(p);

        if (pic == null) {
            return result;
        }
        try {
            String path = String.format("user/%s", p.getUserId());
            customFileUtils.makeFolders(path);
            String target = String.format("%s/%s", path, saveFileName);
            customFileUtils.transferTo(pic, target);
        } catch (Exception e) {
            log.info("에러 발생요! {}", e.getMessage());
            throw new RuntimeException("파일 오류");
        }
        return result;
    }

    public SignInRes postSignIn(SignInPostReq p) {
        User result = mapper.postSignIn(p.getUid());

        if (result == null) {
            throw new RuntimeException("아이디를 확인해주세요.");
        } else if (!BCrypt.checkpw(p.getUpw(), result.getUpw())) {
            throw new RuntimeException("비밀번호를 확인해주세요.");
        }

//        토큰 생성
        MyUser myUser = MyUser.builder()
                .userId(result.getUserId())
                .role("ROLE_USER")
                .build();

        String accessToken = tokenProvider.generateAccessToken(myUser);
        String refreshToken = tokenProvider.generateRefreshToken(myUser);

//        refreshToken은 보안 쿠키를 이용해서 처리


        return SignInRes.builder()
                .userId(result.getUserId())
                .nm(result.getNm())
                .pic(result.getPic())
                .accessToken(accessToken)
                .build();
    }

    @Transactional
    public UserInfoGetRes getUserInfo(UserInfoGetReq p) {
        p.setSignedUserId(authenticationFacade.getLogInUserId());
        return mapper.selProfileUserInfo(p);
    }

    @Transactional
    public String patchProfilePic(UserProfilePatchReq p) {
        p.setSignedUserId(authenticationFacade.getLogInUserId());

        String fileName = customFileUtils.makeRandomFileName(p.getPic());

        p.setPicName(fileName);
        mapper.updProfilePic(p);


        try {
            String midPath = String.format("user/%s", p.getSignedUserId());
//            String delAbsoluteFolderPath = String.format("%s%s", customFileUtils.uploadPath, midPath);
            customFileUtils.deleteFolder(midPath);

            customFileUtils.makeFolders(midPath);
            String filePath = String.format("%s/%s", midPath, fileName);
            customFileUtils.transferTo(p.getPic(), filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return fileName;
    }
}