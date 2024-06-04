package com.green.greengram.feedComment;

import com.green.greengram.feedComment.model.FeedCommentDelReq;
import com.green.greengram.feedComment.model.FeedCommentGetRes;
import com.green.greengram.feedComment.model.FeedCommentPostReq;

import java.util.List;

public interface FeedCommentService {
    long postFeedComment(FeedCommentPostReq p);
    int deleteFeedComment(FeedCommentDelReq p);
    List<FeedCommentGetRes> getFeedComment(long feedId);
}
