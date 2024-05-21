package com.green.greengram.feed.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedPostRes {
    private long feedId;
    @Builder.Default
    private List<String> pics = new ArrayList<>();
}
