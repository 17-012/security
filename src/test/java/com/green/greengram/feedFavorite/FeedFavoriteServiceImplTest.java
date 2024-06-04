package com.green.greengram.feedFavorite;

import com.green.greengram.feedFavorite.intf.FeedFavoriteService;
import com.green.greengram.feedFavorite.model.FeedFavoriteToggleReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import({FeedFavoriteServiceImpl.class})
class FeedFavoriteServiceImplTest {

    @MockBean
    private FeedFavoriteMapper mapper;

    @Autowired
    private FeedFavoriteService service;


    @Test
    void toggleFavorite() {
        FeedFavoriteToggleReq p1 = new FeedFavoriteToggleReq();
        p1.setFeedId(1);
        p1.setUserId(2);
        FeedFavoriteToggleReq p2 = new FeedFavoriteToggleReq();
        p1.setFeedId(10);
        p1.setUserId(20);
        given(mapper.delFavorite(p1)).willReturn(0);  // 0을 리턴해줘
        given(mapper.delFavorite(p2)).willReturn(1); // 1을 리턴해줘

        given(mapper.insFavorite(p1)).willReturn(100);
        given(mapper.insFavorite(p2)).willReturn(200);

        int result1 = service.toggleFavorite(p1);
        assertEquals(100,result1);

        int result2 = service.toggleFavorite(p2);
        assertEquals(0,result2);

        verify(mapper, times(1)).delFavorite(p1);
        verify(mapper, times(1)).delFavorite(p2);

        verify(mapper, times(1)).insFavorite(p1);
        verify(mapper, never()).insFavorite(p2);  // p2일때는 실행되면 안됨
    }
}