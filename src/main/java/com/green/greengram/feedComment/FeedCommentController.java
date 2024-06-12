package com.green.greengram.feedComment;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedComment.model.FeedCommentDelReq;
import com.green.greengram.feedComment.model.FeedCommentGetRes;
import com.green.greengram.feedComment.model.FeedCommentPostReq;

import java.util.List;

public interface FeedCommentController {
    ResultDto<Long> postFeedComment(FeedCommentPostReq p);
    ResultDto<List<FeedCommentGetRes>> getFeedComment(long feedId);
    ResultDto<Integer> deleteFeedComment(FeedCommentDelReq p);

}
