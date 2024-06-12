package com.green.greengram.feed;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feed.model.FeedGetReq;
import com.green.greengram.feed.model.FeedGetRes;
import com.green.greengram.feed.model.FeedPostReq;
import com.green.greengram.feed.model.FeedPostRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.service.GenericResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "피드 컨트롤러", description = "피드 CRUD")
@RequestMapping("api/feed")
public class FeedControllerImpl {
    private final FeedServiceImpl service;

    @PostMapping
    public ResultDto<FeedPostRes> postFeed(@RequestPart(required = false) List<MultipartFile> pics,
                                           @RequestPart FeedPostReq p) {
        FeedPostRes result = service.postFeed(pics, p);
        return ResultDto.<FeedPostRes>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("피드 추가 성공")
                .resultData(result)
                .build();
    }
    @GetMapping
    public ResultDto<List<FeedGetRes>> getFeed(@ParameterObject @ModelAttribute FeedGetReq p) {
        List<FeedGetRes> result = service.getFeed(p);
        return ResultDto.<List<FeedGetRes>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("피드 리스트 출력")
                .resultData(result)
                .build();
    }
}
