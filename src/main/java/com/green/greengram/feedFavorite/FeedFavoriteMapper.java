package com.green.greengram.feedFavorite;

import com.green.greengram.feedFavorite.model.FeedFavoriteEntity;
import com.green.greengram.feedFavorite.model.FeedFavoriteToggleReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedFavoriteMapper {
    int delFavorite(FeedFavoriteToggleReq p);
    int insFavorite(FeedFavoriteToggleReq p);
    List<FeedFavoriteEntity> selFeedFavorite(FeedFavoriteToggleReq p);
}
