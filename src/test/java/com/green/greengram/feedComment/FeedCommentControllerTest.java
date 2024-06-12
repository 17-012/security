package com.green.greengram.feedComment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.feed.FeedService;
import com.green.greengram.feed.model.FeedPostReq;
import com.green.greengram.feedComment.model.FeedCommentDelReq;
import com.green.greengram.feedComment.model.FeedCommentGetRes;
import com.green.greengram.feedComment.model.FeedCommentPostReq;
import com.green.greengram.userfollow.impl.FollowControllerImpl;
import com.green.greengram.userfollow.intf.CharEncodingConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(CharEncodingConfiguration.class)
@WebMvcTest(FeedCommentControllerImpl.class)
class FeedCommentControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private FeedCommentService service;
    private final String BASE_URL = "/api/feed/comment";

    void proc(FeedCommentPostReq p, Map<String, Object> result) throws Exception {
        long resultData = (long) result.get("resultData");
        given(service.postFeedComment(p)).willReturn(resultData);
        String json = mapper.writeValueAsString(p);
        String expected = mapper.writeValueAsString(result);
        mvc.perform(
                        post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andDo(print());
        verify(service).postFeedComment(p);
    }

    Map map(HttpStatus data1, String data2, Object data3) {
        Map<String, Object> result1 = new HashMap<>();
        result1.put("statusCode", data1);
        result1.put("resultMsg", data2);
        result1.put("resultData", data3);

        return result1;
    }

    @Test
    void postFeedComment() throws Exception {
        FeedCommentPostReq p = new FeedCommentPostReq();
        p.setFeedId(1);
        p.setUserId(1);
        p.setComment("CommentTest");
        long result = 1;

        Map<String, Object> result1 = map(HttpStatus.OK, "댓글 달기 성공!", result);
        proc(p, result1);
    }

    @Test
    void getFeedComment() throws Exception{
        long result = 1;
        FeedCommentGetRes p = new FeedCommentGetRes();
        p.setFeedCommentId(1);
        p.setComment("CommentTest");p.setComment("CommentTest");

        FeedCommentGetRes p1 = new FeedCommentGetRes();
        p1.setFeedCommentId(2);
        p1.setComment("CommentTest");
        List<FeedCommentGetRes> list = new ArrayList<>();
        list.add(p);
        list.add(p1);

        Map<String, Object> result1 = map(HttpStatus.OK, "", list);
        given(service.getFeedComment(result)).willReturn(list);

        String expected = mapper.writeValueAsString(result1);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("feed_id", String.valueOf(result));
        mvc.perform(
                        get(BASE_URL)
                                .params(params)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andDo(print());
        verify(service).getFeedComment(result);
    }

    @Test
    void deleteFeedComment() throws Exception {
        FeedCommentDelReq p = new FeedCommentDelReq(1,2);
        int result = 1;
        Map<String, Object> result1 = map(HttpStatus.OK, result == 1 ? "댓글 삭제 성공" : "댓글 삭제 실패", result);
        given(service.deleteFeedComment(p)).willReturn(result);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("feed_comment_id", String.valueOf(p.getFeedCommentId()));
        params.add("signed_user_id", String.valueOf(p.getSignedUserId()));
        String expected = mapper.writeValueAsString(result1);
        mvc.perform(
                delete(BASE_URL)
                        .params(params)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andDo(print());
        verify(service).deleteFeedComment(p);
    }
}