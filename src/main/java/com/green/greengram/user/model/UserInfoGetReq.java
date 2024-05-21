package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
public class UserInfoGetReq {

    @Parameter(name = "signed_user_id")
    private long signedUserId;
    @Parameter(name = "profile_user_id")
    private long profileUserId;

    @ConstructorProperties({"signed_user_id","profile_user_id"})
    public UserInfoGetReq(long signedUserId, long profileUserId) {
        this.signedUserId = signedUserId;
        this.profileUserId = profileUserId;
    }
}
