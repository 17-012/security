package com.green.greengram.feedComment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedCommentPostReq {
    private long feedId;
    private long userId;
    private String comment;

    @JsonIgnore
    private long commentId;
}
