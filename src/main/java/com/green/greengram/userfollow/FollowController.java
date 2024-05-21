package com.green.greengram.userfollow;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.userfollow.model.FollowPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/user/follow")
@RestController
public class FollowController {
    private final FollowService service;

    @PostMapping
    public ResultDto<Integer> postFollow(@RequestBody FollowPostReq p) {
        int result = service.postFollow(p);
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("팔로우 성공")
                .resultData(result)
                .build();
    }

    @DeleteMapping
    public ResultDto<Integer> deleteFollow(@ParameterObject @ModelAttribute FollowPostReq p) {
        int result = service.deleteFollow(p);
        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("팔로우 취소")
                .resultData(result)
                .build();
    }

}
