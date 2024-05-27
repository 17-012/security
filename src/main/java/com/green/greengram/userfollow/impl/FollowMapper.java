package com.green.greengram.userfollow.impl;

import com.green.greengram.userfollow.model.FollowPostReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowMapper {
    int delFollow(FollowPostReq p);
    int insFollow(FollowPostReq p);
}
