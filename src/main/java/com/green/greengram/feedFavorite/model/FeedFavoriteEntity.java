package com.green.greengram.feedFavorite.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class FeedFavoriteEntity {
    private long feedId;
    private long userId;
    private String createdAt;
}
