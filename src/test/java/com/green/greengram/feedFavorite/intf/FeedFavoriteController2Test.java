package com.green.greengram.feedFavorite.intf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedFavorite.FeedFavoriteControllerImpl;
import com.green.greengram.feedFavorite.model.FeedFavoriteToggleReq;
import com.green.greengram.userfollow.intf.CharEncodingConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(CharEncodingConfiguration.class)
@WebMvcTest(FeedFavoriteControllerImpl.class)
class FeedFavoriteController2Test {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private FeedFavoriteController controller;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private FeedFavoriteService service;

    private final String BASE_URL = "/api/feed/favorite";

    void proc(FeedFavoriteToggleReq p, Map<String, Object> result) throws Exception {
        int resultData = (int) result.get("resultData");
        given(service.toggleFavorite(p)).willReturn(resultData);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("feed_id", String.valueOf(p.getFeedId()));
        params.add("user_id", String.valueOf(p.getUserId()));
        String resultJson = mapper.writeValueAsString(result);
        mvc.perform(
                        get(BASE_URL)
                                .params(params)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson))
                .andDo(print());
        verify(service).toggleFavorite(p);
    }

    Map map(HttpStatus data1, String data2, Object data3) {
        Map<String, Object> result1 = new HashMap<>();
        result1.put("statusCode", data1);
        result1.put("resultMsg", data2);
        result1.put("resultData", data3);
        return result1;
    }

    @Test
    void toggleFavorite() throws Exception {
        FeedFavoriteToggleReq p = new FeedFavoriteToggleReq(5, 6);
        int result = 1;

        Map<String, Object> result1 = map(HttpStatus.OK, (result == 0 ? "좋아요 취소" : "좋아요"), result);
        proc(p, result1);
    }

    @Test
    void test1() throws Exception {
        FeedFavoriteToggleReq p = new FeedFavoriteToggleReq(1, 2);
        int result = 0;
        Map<String, Object> result1 = map(HttpStatus.OK, (result == 0 ? "좋아요 취소" : "좋아요"), result);
        proc(p, result1);
    }

    @Test
    void test2() throws Exception {
        FeedFavoriteToggleReq p = new FeedFavoriteToggleReq(1, 2);
        int result = 2;
        Map<String, Object> result1 = map(HttpStatus.OK, (result == 0 ? "좋아요 취소" : "좋아요"), result);
        proc(p, result1);
    }
}