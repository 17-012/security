package com.green.greengram.userfollow.impl;

import com.green.greengram.security.AuthenticationFacade;
import com.green.greengram.userfollow.intf.FollowService;
import com.green.greengram.userfollow.model.FollowPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowMapper mapper;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public int postFollow(FollowPostReq p) {
        p.setFromUserId(authenticationFacade.getLogInUserId());
        return mapper.insFollow(p);
    }

    @Override
    public int deleteFollow(FollowPostReq p) {
        p.setFromUserId(authenticationFacade.getLogInUserId());
        return mapper.delFollow(p);
    }

}
