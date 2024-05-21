package com.green.greengram.feedComment.model;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@ToString
public class FeedCommentDelReq {
    @Parameter(name = "feed_comment_id")
    private long feedCommentId;
    @Parameter(name = "signed_user_id")
    private long signedUserId;

    @ConstructorProperties({"feed_comment_id", "signed_user_id"})
    public FeedCommentDelReq(long feedCommentId, long signedUserId) {
        this.feedCommentId = feedCommentId;
        this.signedUserId = signedUserId;
    }
}
