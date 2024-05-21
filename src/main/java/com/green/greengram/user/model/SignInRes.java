package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRes {
    @Schema(example = "1", description = "유저 PK")
    private long userId;
    @Schema(example = "홍길동", description = "유저 이름")
    private String nm;
    @Schema(example = "fdc13560-7696-440f-95ab-61f2d0b62729.jpg",description = "유저 프로필 사진")
    private String pic;

}
