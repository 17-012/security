package com.green.greengram.feedFavorite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
//    @Parameter(name = "user_id")
    @JsonIgnore
    private long userId;

    @ConstructorProperties({"feed_id"})
    public FeedFavoriteToggleReq(long feedId) {
        this.feedId = feedId;
    }
}
