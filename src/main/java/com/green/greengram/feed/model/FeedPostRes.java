package com.green.greengram.feed.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FeedPostRes {
    private long feedId;
    @Builder.Default
    private List<String> pics = new ArrayList<>();
}
