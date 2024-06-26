package com.green.greengram.feedComment;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedComment.model.FeedCommentDelReq;
import com.green.greengram.feedComment.model.FeedCommentGetRes;
import com.green.greengram.feedComment.model.FeedCommentPostReq;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/feed/comment")
public class FeedCommentControllerImpl implements FeedCommentController {
    private final FeedCommentService service;

    @PostMapping
    @Override
    public ResultDto<Long> postFeedComment(@RequestBody FeedCommentPostReq p) {
        long feedCommentId = service.postFeedComment(p);
        return ResultDto.<Long>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("댓글 달기 성공!")
                .resultData(feedCommentId)
                .build();
    }

    @DeleteMapping
    @Override
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "dfs", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "400", description = "asd", useReturnTypeSchema = false)
    })
    public ResultDto<Integer> deleteFeedComment(@ParameterObject @ModelAttribute FeedCommentDelReq p) {
        int result = service.deleteFeedComment(p);
        String msg = result == 1 ? "댓글 삭제 성공" : "댓글 삭제 실패";
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping
    @Override
    public ResultDto<List<FeedCommentGetRes>> getFeedComment(@RequestParam(name = "feed_id") long feedId) {
        List<FeedCommentGetRes> result = service.getFeedComment(feedId);

        return ResultDto.<List<FeedCommentGetRes>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("")
                .resultData(result)
                .build();
    }
}