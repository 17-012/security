package com.green.greengram.feedComment;

import com.green.greengram.feedComment.model.FeedCommentDelReq;
import com.green.greengram.feedComment.model.FeedCommentGetRes;
import com.green.greengram.feedComment.model.FeedCommentPostReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import({FeedCommentServiceImpl.class})
class FeedCommentServiceImplTest {

    @MockBean
    private FeedCommentMapper mapper;

    @Autowired
    private FeedCommentService service;


    @Test
    void postFeedComment() {
        FeedCommentPostReq p1 = new FeedCommentPostReq();
        given(mapper.postFeedComment(p1)).willReturn(0L);
        long result = service.postFeedComment(p1);
        assertEquals(0, result);

        FeedCommentPostReq p2 = new FeedCommentPostReq();
        given(mapper.postFeedComment(p2)).willReturn(1L);
        long result2 = service.postFeedComment(p2);
        assertEquals(0, result2);

        verify(mapper).postFeedComment(p1);
        verify(mapper).postFeedComment(p2);
    }

    @Test
    void deleteFeedComment() {
        FeedCommentDelReq p1 = new FeedCommentDelReq(1,2);
        FeedCommentDelReq p2 = new FeedCommentDelReq(100,200);
        given(mapper.deleteFeedComment(p1)).willReturn(0);
        given(mapper.deleteFeedComment(p2)).willReturn(1);
        int result = service.deleteFeedComment(p1);
        int result2 = service.deleteFeedComment(p2);
        assertEquals(0, result);
        assertEquals(1, result2);
        verify(mapper, times(1)).deleteFeedComment(p1);
        verify(mapper, times(1)).deleteFeedComment(p2);
    }

    @Test
    void getFeedComment() {
        List<FeedCommentGetRes> list = new ArrayList<>();

        FeedCommentGetRes res1 = new FeedCommentGetRes();
        FeedCommentGetRes res2 = new FeedCommentGetRes();
        FeedCommentGetRes res3 = new FeedCommentGetRes();

        list.add(res1);
        list.add(res2);
        list.add(res3);

        res1.setFeedCommentId(10);
        res2.setFeedCommentId(20);
        res3.setFeedCommentId(30);

        res1.setComment("1");
        res2.setComment("2");
        res3.setComment("3");

        List<FeedCommentGetRes> list2 = new ArrayList<>();

        given(mapper.getFeedComment(5)).willReturn(list);
        given(mapper.getFeedComment(7)).willReturn(list2);

        List<FeedCommentGetRes> result1 = service.getFeedComment(5);
        List<FeedCommentGetRes> result2 = service.getFeedComment(7);

        assertEquals(list, result1);
        assertEquals(list2.size(), result2.size());

        verify(mapper, times(1)).getFeedComment(5);
        verify(mapper, times(1)).getFeedComment(7);
    }
}