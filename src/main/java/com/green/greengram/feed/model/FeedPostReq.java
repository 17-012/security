package com.green.greengram.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FeedPostReq {
    @JsonIgnore
    private long feedId;
    @Schema(example = "17012", description = "유저 PK")
    private long userId;
    @Schema(example = "가나다라마바사", description = "피드 내용")
    private String contents;
    @Schema(example = "대구 중구", description = "현재 사용자 위치")
    private String location;
}
