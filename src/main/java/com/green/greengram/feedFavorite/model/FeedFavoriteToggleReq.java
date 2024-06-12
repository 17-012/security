package com.green.greengram.feedFavorite.model;

import com.green.greengram.feedFavorite.intf.FeedFavoriteService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;

import java.beans.ConstructorProperties;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FeedFavoriteToggleReq {
    @Parameter(name = "feed_id")
    private long feedId;
    @Parameter(name = "user_id")
    private long userId;

    @ConstructorProperties({"feed_id", "user_id"})
    public FeedFavoriteToggleReq(long feedId, long userId) {
        this.feedId = feedId;
        this.userId = userId;
    }
}
