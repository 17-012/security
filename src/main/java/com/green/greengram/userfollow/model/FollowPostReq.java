package com.green.greengram.userfollow.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@Setter
public class FollowPostReq {
    @Schema(example = "1", description = "팔로우 할 or 팔로우 취소 할 user PK")
    @Parameter(name = "from_user_id")
    private long fromUserId;
    @Schema(example = "2", description = "팔로잉 할 or 팔로잉 취소 할 user PK")
    @Parameter(name = "to_user_id")
    private long toUserId;

    public FollowPostReq(@BindParam("from_user_id") long fromUserId,
                         @BindParam("to_user_id") long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
}
