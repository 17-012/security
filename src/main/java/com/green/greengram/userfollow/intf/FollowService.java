package com.green.greengram.userfollow.intf;

import com.green.greengram.userfollow.model.FollowPostReq;

public interface FollowService {

    int postFollow(FollowPostReq p);
    int deleteFollow(FollowPostReq p);
}
