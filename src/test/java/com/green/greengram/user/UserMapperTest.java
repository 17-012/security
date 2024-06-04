package com.green.greengram.user;

import com.green.greengram.user.model.User;
import com.green.greengram.user.model.UserInfoGetReq;
import com.green.greengram.user.model.UserInfoGetRes;
import com.green.greengram.user.model.UserProfilePatchReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@ActiveProfiles("tdd")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {

    @Autowired
    private UserMapper mapper;

    @Test
    void postSignUp() {
    }

    @Test
    @DisplayName("유저 로그인 테스트")
    void postSignIn() {
        User user = mapper.postSignIn("1");
        List<User> userList = mapper.selTest(user.getUserId());
        User user1Comp = userList.get(0);
        assertEquals(user.getUserId(), user1Comp.getUserId());

        User user3 = mapper.postSignIn("3");
        List<User> userList3 = mapper.selTest(user3.getUserId());
        User user3Comp = userList3.get(0);
        assertEquals(user3.getUserId(), user3Comp.getUserId());

        User userNo = mapper.postSignIn("asdasdqwxzc");
        assertNull(userNo, "없는 사용자가 넘어 옴");
    }

    @Test
    @DisplayName("상대 유저 프로필 테스트")
    void selProfileUserInfo() {
        UserInfoGetRes user = mapper.selProfileUserInfo(new UserInfoGetReq(1, 2));
        assertEquals("유부유부", user.getNm(), "유저 이름이 실제와 다르다.");
        assertEquals(5, user.getFeedCnt(), "실제 피드 갯수가 다릅니다.");
        assertEquals(3, user.getFollowState(), "실제 follow 값과 다릅니다.");

        UserInfoGetRes user1 = mapper.selProfileUserInfo(new UserInfoGetReq(9, 9));
        assertNull(user1, "다른 데이터가 불러와짐");
    }

    @Test
    void updProfilePic() {

        UserProfilePatchReq req = new UserProfilePatchReq();
        req.setSignedUserId(1);
        req.setPicName("asd");

        List<User> selUser = mapper.selTest(req.getSignedUserId());
        assertEquals("64440c58-08ac-41ca-89e3-32f8f9b7b059.jpg", selUser.get(0).getPic(), "pic 값이 다름");
        mapper.updProfilePic(req);

        selUser = mapper.selTest(req.getSignedUserId());
        assertEquals(req.getPicName(), selUser.get(0).getPic(), "데이터 변경되지 않음");

        req.setSignedUserId(99);
        selUser = mapper.selTest(req.getSignedUserId());
        assertEquals(0, selUser.size(), "없어야 할 데이터가 있음");

    }
}