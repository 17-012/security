package com.green.greengram.feed;

import com.green.greengram.feed.model.FeedGetReq;
import com.green.greengram.feed.model.FeedGetRes;
import com.green.greengram.feed.model.FeedPicPostDto;
import com.green.greengram.feed.model.FeedPostReq;
import com.green.greengram.feedComment.model.FeedCommentGetRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {
    void postFeed(FeedPostReq p);

    void postFeedPics(FeedPicPostDto p);

    List<FeedGetRes> getFeed(FeedGetReq p);

    List<String> getFeedPics(long feedId);

    List<FeedCommentGetRes> getFeedCommentTopBy4FeedId(long feedId);
}
