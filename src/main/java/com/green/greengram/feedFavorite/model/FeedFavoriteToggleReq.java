package com.green.greengram.feedFavorite.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedFavoriteToggleReq {
    private long feedId;
    private long userId;
}
