package com.green.greengram.feedFavorite;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedFavorite.model.FeedFavoriteToggleReq;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/feed/favorite")
public class FeedFavoriteController {
    private final FeedFavoriteService service;

    @GetMapping
    @Operation(summary = "좋아요", description = "Toggle 처리")
    public ResultDto<Integer> toggleFavorite(@ParameterObject @ModelAttribute FeedFavoriteToggleReq p) {
        int result = service.toggleFavorite(p);
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(result == 0 ? "좋아요 취소" : "좋아요")
                .resultData(result)
                .build();
    }


}
