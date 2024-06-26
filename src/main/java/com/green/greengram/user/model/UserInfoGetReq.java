package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class UserInfoGetReq {

//    @Parameter(name = "signed_user_id")
    @JsonIgnore
    private long signedUserId;
    @Parameter(name = "profile_user_id")
    private long profileUserId;

    @ConstructorProperties({"profile_user_id"})
    public UserInfoGetReq(long profileUserId) {
        this.profileUserId = profileUserId;
    }
}
