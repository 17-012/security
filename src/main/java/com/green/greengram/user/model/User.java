package com.green.greengram.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private long userId;
    private String uid;
    private String nm;
    private String upw;
    private String pic;
    private String createdAt;
    private String updatedAt;
}
