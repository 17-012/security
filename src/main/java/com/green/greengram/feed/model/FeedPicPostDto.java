package com.green.greengram.feed.model;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class FeedPicPostDto {
    private long feedId;
    @Builder.Default
    private List<String> fileNames = new ArrayList<>();
}
