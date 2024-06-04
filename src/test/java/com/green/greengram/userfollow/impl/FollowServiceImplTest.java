package com.green.greengram.userfollow.impl;

import com.green.greengram.userfollow.intf.FollowService;
import com.green.greengram.userfollow.model.FollowPostReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@Import({FollowServiceImpl.class})
@ExtendWith(SpringExtension.class)
class FollowServiceImplTest {

    @MockBean
    private FollowMapper mapper;

    @Autowired
    private FollowService service;

    @Test
    void postFollow() {
        FollowPostReq p1 = new FollowPostReq(1, 2);
        FollowPostReq p2 = new FollowPostReq(1, 2);
        FollowPostReq p3 = new FollowPostReq(1, 2);

        given(mapper.insFollow(p1)).willReturn(0);
        given(mapper.insFollow(p2)).willReturn(0);
        given(mapper.insFollow(p3)).willReturn(0);

        int result = service.postFollow(p1);
        int result1 = service.postFollow(p2);
        int result2 = service.postFollow(p3);

        assertEquals(0, result);
        assertEquals(0, result1);
        assertEquals(0, result2);

        verify(mapper).insFollow(p1);
        verify(mapper).insFollow(p2);
        verify(mapper).insFollow(p3);
    }

    @Test
    void deleteFollow() {

        FollowPostReq p1 = new FollowPostReq(1, 2);
        FollowPostReq p2 = new FollowPostReq(3, 4);
        FollowPostReq p3 = new FollowPostReq(5, 4);

        given(mapper.delFollow(p1)).willReturn(0);
        given(mapper.delFollow(p2)).willReturn(1);
        given(mapper.delFollow(p3)).willReturn(2);

        int result = service.deleteFollow(p1);
        int result1 = service.deleteFollow(p2);
        int result2 = service.deleteFollow(p3);

        assertEquals(0, result);
        assertEquals(1, result1);
        assertEquals(2, result2);

        verify(mapper).delFollow(p1);
        verify(mapper).delFollow(p2);
        verify(mapper).delFollow(p3);

    }
}