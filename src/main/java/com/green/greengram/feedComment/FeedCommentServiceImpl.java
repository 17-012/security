package com.green.greengram.feedComment;

import com.green.greengram.common.GlobalConst;
import com.green.greengram.feedComment.model.FeedCommentDelReq;
import com.green.greengram.feedComment.model.FeedCommentGetRes;
import com.green.greengram.feedComment.model.FeedCommentPostReq;
import com.green.greengram.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentServiceImpl implements FeedCommentService{
    private final FeedCommentMapper mapper;
    private final AuthenticationFacade authenticationFacade;

    @Transactional
    @Override
    public long postFeedComment(FeedCommentPostReq p) {
        p.setUserId(authenticationFacade.getLogInUserId());
        mapper.postFeedComment(p);
        return p.getCommentId();
    }

    @Override
    public int deleteFeedComment(FeedCommentDelReq p) {
        p.setSignedUserId(authenticationFacade.getLogInUserId());
        return mapper.deleteFeedComment(p);
    }

    @Override
    public List<FeedCommentGetRes> getFeedComment(long feedId) {
        List<FeedCommentGetRes> result = mapper.getFeedComment(feedId);
        if(result.size() > 4) {
            result.subList(0, GlobalConst.COMMENT_SIZE_PER_FEED - 1).clear();
        }
        return result;
    }
}
