package com.green.greengram.userfollow;

import com.green.greengram.userfollow.model.FollowPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowMapper mapper;

    @Transactional
    public int postFollow(FollowPostReq p) {
        return mapper.insFollow(p);
    }

    @Transactional
    public int deleteFollow(FollowPostReq p) {
        return mapper.delFollow(p);
    }

}
