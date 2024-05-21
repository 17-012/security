package com.green.greengram.feedComment;

import com.green.greengram.common.GlobalConst;
import com.green.greengram.feed.model.FeedPostReq;
import com.green.greengram.feedComment.model.FeedCommentDelReq;
import com.green.greengram.feedComment.model.FeedCommentGetRes;
import com.green.greengram.feedComment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentMapper mapper;

    @Transactional
    public long postFeedComment(FeedCommentPostReq p) {
        mapper.postFeedComment(p);
        return p.getCommentId();
    }

    public int deleteFeedComment(FeedCommentDelReq p) {
        return mapper.deleteFeedComment(p);
    }

    public List<FeedCommentGetRes> getFeedComment(long feedId) {
        List<FeedCommentGetRes> result = mapper.getFeedComment(feedId);
        result.subList(0, GlobalConst.COMMENT_SIZE_PER_FEED - 1).clear();
        return result;
    }
}
