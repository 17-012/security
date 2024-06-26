package com.green.greengram.feedComment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@ToString
@Setter
@EqualsAndHashCode
public class FeedCommentDelReq {
    @Parameter(name = "feed_comment_id")
    private long feedCommentId;
//    @Parameter(name = "signed_user_id")
    @JsonIgnore
    private long signedUserId;

    @ConstructorProperties({"feed_comment_id"})
    public FeedCommentDelReq(long feedCommentId) {
        this.feedCommentId = feedCommentId;
    }
}
