package com.green.greengram.userfollow.model;

import lombok.*;

// 다른 객체주소값이여도 안에 값이 같으면 true 반환
@EqualsAndHashCode

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FollowEntity {

    private long fromUserId;
    private long toUserId;
    private String createdAt;
}

