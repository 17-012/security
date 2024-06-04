package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class SignInPostReq {
    @Schema(example = "1212", description = "유저 아이디")
    private String uid;
    @Schema(example = "1212", description = "유저 비밀번호")
    private String upw;
}