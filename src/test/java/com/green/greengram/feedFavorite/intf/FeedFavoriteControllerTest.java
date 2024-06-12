package com.green.greengram.feedFavorite.intf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedFavorite.FeedFavoriteControllerImpl;
import com.green.greengram.feedFavorite.model.FeedFavoriteToggleReq;
import com.green.greengram.userfollow.impl.FollowControllerImpl;
import com.green.greengram.userfollow.intf.CharEncodingConfiguration;
import com.green.greengram.userfollow.intf.FollowController;
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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Import(CharEncodingConfiguration.class)
@WebMvcTest(FeedFavoriteControllerImpl.class)
class FeedFavoriteControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private FeedFavoriteController controller;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private FeedFavoriteService service;

    private final String BASE_URL = "/api/feed/favorite";

    @Test
    void toggleFavorite() throws Exception {
        FeedFavoriteToggleReq p = new FeedFavoriteToggleReq(1,3);
        int result = 1;
        given(service.toggleFavorite(p)).willReturn(result);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("feed_id", String.valueOf(p.getFeedId()));
        params.add("user_id", String.valueOf(p.getUserId()));

        ResultDto<Integer> expectedResult = ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(result == 0 ? "좋아요 취소" : "좋아요")
                .resultData(result)
                .build();
        String expectedJson = mapper.writeValueAsString(expectedResult);

        Map<String,Object> result1 = new HashMap<>();
        result1.put("statusCode", HttpStatus.OK);
        result1.put("resultMsg", result == 0 ? "좋아요 취소" : "좋아요");
        result1.put("resultData", result);
        String resultJson = mapper.writeValueAsString(result1);
        mvc.perform(
                get(BASE_URL)
                        .params(params)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson))
                .andDo(print());
        verify(service).toggleFavorite(p);
    }
}