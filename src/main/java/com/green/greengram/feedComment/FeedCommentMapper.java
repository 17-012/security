package com.green.greengram.feedComment;

import com.green.greengram.feedComment.model.FeedCommentDelReq;
import com.green.greengram.feedComment.model.FeedCommentGetRes;
import com.green.greengram.feedComment.model.FeedCommentPostReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedCommentMapper {
    void postFeedComment(FeedCommentPostReq p);
    int deleteFeedComment(FeedCommentDelReq p);
    List<FeedCommentGetRes> getFeedComment (long feedId);
}