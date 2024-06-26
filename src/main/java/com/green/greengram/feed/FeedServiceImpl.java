package com.green.greengram.feed;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.common.GlobalConst;
import com.green.greengram.feed.model.*;
import com.green.greengram.feedComment.model.FeedCommentGetRes;
import com.green.greengram.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService{
    private final FeedMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final AuthenticationFacade authenticationFacade;
    @Override
    @Transactional
    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {
        p.setUserId(authenticationFacade.getLogInUserId());
        mapper.postFeed(p);
        FeedPicPostDto picDto;
        if (pics == null || pics.size() == 0) {
            return FeedPostRes.builder()
                    .feedId(p.getFeedId())
                    .build();
        }
        try {
            String path = String.format("/feed/%s", p.getFeedId());
            customFileUtils.makeFolders(path);
            picDto = FeedPicPostDto.builder().feedId(p.getFeedId()).build();
            for (MultipartFile pic : pics) {
                String fileName = customFileUtils.makeRandomFileName(pic);
                picDto.getFileNames().add(fileName);
                String filePath = String.format("%s/%s", path, fileName);
                customFileUtils.transferTo(pic, filePath);
            }
            mapper.postFeedPics(picDto);
        } catch (Exception e) {
            log.info(e.getMessage());
            log.info(e.getStackTrace().toString());
            throw new RuntimeException("사진 저장 실패");
        }
        return FeedPostRes.builder()
                .feedId(p.getFeedId())
                .pics(picDto.getFileNames())
                .build();
    }

    @Override
    public List<FeedGetRes> getFeed(FeedGetReq p) {
        p.setSignedUserId(authenticationFacade.getLogInUserId());
        List<FeedGetRes> result = mapper.getFeed(p);
        for (FeedGetRes r : result) {
            r.setPics(mapper.getFeedPics(r.getFeedId()));
            List<FeedCommentGetRes> comments = mapper.getFeedCommentTopBy4FeedId(r.getFeedId());
            if (comments.size() == GlobalConst.COMMENT_SIZE_PER_FEED) {
                r.setIsMoreComment(1);
                comments.remove(GlobalConst.COMMENT_SIZE_PER_FEED - 1);
            }
            r.setComments(comments);
        }
        return result;
    }
}
