package com.green.greengram.feed.model;

import com.green.greengram.feedComment.model.FeedCommentGetRes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
//@EqualsAndHashCode
public class FeedGetRes {
    @Schema(example = "5", description = "피드 PK")
    private long feedId;
    @Schema(example = "피드 내용")
    private String contents;
    @Schema(example = "현재 지역")
    private String location;
    @Schema(example = "피드 생성일")
    private String createdAt;
    @Schema(example = "피드 작성자 PK")
    private long writerId;
    @Schema(example = "피드 작성자 프로필 사진")
    private String writerPic;
    @Schema(example = "피드 작성자 이름")
    private String writerNm;
    @Schema(example = "로그인한 사용자의 좋아요 상태")
    private int isFav;

    private List<String> pics;
    private List<FeedCommentGetRes> comments;
    @Schema(example = "댓글이 더 많은지 나타내는 상태")
    private int isMoreComment;


}
