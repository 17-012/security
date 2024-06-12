package com.green.greengram.userfollow.intf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.model.ResultDto;
import com.green.greengram.userfollow.impl.FollowControllerImpl;
import com.green.greengram.userfollow.impl.FollowServiceImpl;
import com.green.greengram.userfollow.model.FollowPostReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(CharEncodingConfiguration.class)
@WebMvcTest(FollowControllerImpl.class)
class FollowControllerTest {



    @Autowired
    private MockMvc mvc; // 포스트맨 처럼 통신 날리는 역할

    @MockBean
    private FollowService service;

    // 객체를 자동으로 JSON으로 변환시켜줄때 사용
    @Autowired
    private ObjectMapper mapper;

    private final String BASE_URL = "/api/user/follow";

    @Test
    void postFollow() throws Exception {
        FollowPostReq p = new FollowPostReq(1, 2);
        given(service.postFollow(p)).willReturn(1);
        String json = mapper.writeValueAsString(p);
        ResultDto<Integer> expectedResult = ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(1)
                .build();
        String expectedJson = mapper.writeValueAsString(expectedResult);
        mvc.perform(
                        post(BASE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson))
                .andDo(print());
        verify(service).postFollow(p);

//        Map expectedMap = new HashMap();
//        expectedMap.put("statusCode", HttpStatus.OK);
//        expectedMap.put("resultMsg", HttpStatus.OK.toString());
//        expectedMap.put("resultData", 1);
//        String expectedMapJson = mapper.writeValueAsString(expectedMap);
//
//        mvc.perform(
//                        post("/api/user/follow")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(json)
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().string(expectedMapJson))
//                .andDo(print());
//        verify(service).postFollow(p);
    }

    @Test
    void deleteFollow() throws Exception {
        FollowPostReq p = new FollowPostReq(1, 2);
        int resultData = 10;
        given(service.deleteFollow(p)).willReturn(resultData);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("from_user_id", String.valueOf(p.getFromUserId()));
        params.add("to_user_id", String.valueOf(p.getToUserId()));

        ResultDto<Integer> expectedResult = ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(resultData)
                .build();

        String expectedJson = mapper.writeValueAsString(expectedResult);
        mvc.perform(
                        delete(BASE_URL)
                                .params(params)
                        //쿼리스트링 변환 메서드
                        // "/api/user/follow?from_user_id=1&to_user_id=2"
                )
                .andExpect(status().isOk())
                .andExpect(content().string(expectedJson))
                .andDo(print());
        verify(service).deleteFollow(p);
    }
}