package com.green.greengram.userfollow.impl;

import com.green.greengram.userfollow.model.FollowEntity;
import com.green.greengram.userfollow.model.FollowPostReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FollowMapper {
    int delFollow(FollowPostReq p);
    int insFollow(FollowPostReq p);
    List<FollowEntity> selFollowListForTest(FollowPostReq p);
}
