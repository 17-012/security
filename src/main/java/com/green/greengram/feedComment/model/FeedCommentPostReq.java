package com.green.greengram.feedComment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FeedCommentPostReq {
    private long feedId;
    @JsonIgnore
    private long userId;
    private String comment;

    @JsonIgnore
    private long commentId;
}
