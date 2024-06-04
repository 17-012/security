package com.green.greengram.userfollow.impl;

import com.green.greengram.userfollow.model.FollowEntity;
import com.green.greengram.userfollow.model.FollowPostReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@ActiveProfiles("tdd")
//@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FollowMapperTest {

    @Autowired
    private FollowMapper followMapper;

    @Test
    @DisplayName("유저 팔로우 insert 테스트")
    void insFollow() {
        FollowPostReq p1 = new FollowPostReq(0, 0);
        List<FollowEntity> list1 = followMapper.selFollowListForTest(p1);
        // 기대한 값이 아니면! 아닐때 메세지를 날릴 수 있다.
        assertEquals(7, list1.size(), "1. 레코드 수가 다르다");

        FollowPostReq req = new FollowPostReq(3, 2);
        int result = followMapper.insFollow(req);

//         ( 기대 값 , 실제 값 ) 이 맞는지 확인 하는 코드
        assertEquals(1, result);

        List<FollowEntity> list2 = followMapper.selFollowListForTest(p1);
        // 기대한 값이 아니면! 아닐때 메세지를 날릴 수 있다.
        assertEquals(1, list2.size() - list1.size(), "실제 insert가 되지 않음");

        List<FollowEntity> list3 = followMapper.selFollowListForTest(req);
        assertEquals(1, list3.size(), "값이 제대로 insert되지 않음");
        assertEquals(req.getFromUserId(), list3.get(0).getFromUserId(), "fromUserId 값이 다름");
        assertEquals(req.getToUserId(), list3.get(0).getToUserId(), "toUserId 값이 다름");

        FollowPostReq req2 = new FollowPostReq(4, 1);
        int result2 = followMapper.insFollow(req2);
        assertEquals(1, result2);
        List<FollowEntity> list4 = followMapper.selFollowListForTest(req2);
        assertEquals(1, list4.size(), "값이 제대로 insert되지 않음");
        assertEquals(req2.getFromUserId(), list4.get(0).getFromUserId(), "fromUserId 값이 다름");
        assertEquals(req2.getToUserId(), list4.get(0).getToUserId(), "toUserId 값이 다름");
    }

    @Test
    @DisplayName("유저 팔로우 select 테스트")
    void selFollowListForTest() {
        //1. from user id = 0
        FollowPostReq p1 = new FollowPostReq(0, 0);
        List<FollowEntity> list1 = followMapper.selFollowListForTest(p1);
        // 기대한 값이 아니면! 아닐때 메세지를 날릴 수 있다.
        assertEquals(7, list1.size(), "1. 레코드 수가 다르다");

        FollowEntity record0 = list1.get(0);
        assertEquals(1, record0.getFromUserId(), "1. 0번 레코드 from_user_id 값이 다름");
        assertEquals(2, record0.getToUserId(), "1. 0번 레코드 to_user_id 값이 다름");
        assertEquals(new FollowEntity(1, 2, "2024-05-27 10:06:51"),
                list1.get(0), "1. 0번 레코드 값이 다름");

        //2. from user id = 1
        FollowPostReq p2 = new FollowPostReq(1, 0);
        List<FollowEntity> list2 = followMapper.selFollowListForTest(p2);
        assertEquals(4, list2.size(), "2. 레코드 수가 다르다.");
        assertEquals(new FollowEntity(1, 2, "2024-05-27 10:06:51"), list2.get(0), "2. 0번 레코드 값이 다름");

        //3. 없는 from user id 값을 넣을 때
        FollowPostReq p3 = new FollowPostReq(999, 0);
        List<FollowEntity> list3 = followMapper.selFollowListForTest(p3);
        assertEquals(0, list3.size(), "3. 레코드 수가 다르다.");

        //4. to user id =1
        FollowPostReq p4 = new FollowPostReq(0, 1);
        List<FollowEntity> list4 = followMapper.selFollowListForTest(p4);
        assertEquals(1, list4.size(), "4. 레코드 수가 다르다.");
        assertEquals(new FollowEntity(2, 1, "2024-05-27 10:06:51"), list4.get(0), "4. 0번 레코드 값이 다름");

    }

    @Test
    void delFollow() {
        List<FollowEntity> list1 = followMapper.selFollowListForTest(new FollowPostReq(0, 0));

        FollowPostReq req = new FollowPostReq(1,2);
        int result = followMapper.delFollow(req);
        assertEquals(1, result, "영향받은 행이 없음");

        List<FollowEntity> list2 = followMapper.selFollowListForTest(new FollowPostReq(0,0));
        assertEquals(1, list1.size() - list2.size(), "실제로 삭제되지 않음");

        List<FollowEntity> list3 = followMapper.selFollowListForTest(req);
        assertEquals(0, list3.size(), "실제 데이터가 삭제 됨");

        FollowPostReq req2 = new FollowPostReq(99,98);
        int result2 = followMapper.delFollow(req2);
        assertEquals(0, result2, "없는 데이터가 삭제 됨");


        FollowPostReq req3 = new FollowPostReq(2,5);
        int result3 = followMapper.delFollow(req3);
        assertEquals(1, result3, "삭제되지 않음");

        List<FollowEntity> list4 = followMapper.selFollowListForTest(new FollowPostReq(0,0));
        assertEquals(1, list2.size() - list4.size(), "실제 데이터가 삭제되지 않음");
        
        List<FollowEntity> list5 = followMapper.selFollowListForTest(req3);
        assertEquals(0,list5.size(), "실제 원하는 데이터가 삭제 안됨");

    }
}