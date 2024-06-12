package com.green.greengram.feedFavorite.intf;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedFavorite.model.FeedFavoriteToggleReq;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface FeedFavoriteController {
    ResultDto<Integer> toggleFavorite(@ParameterObject @ModelAttribute FeedFavoriteToggleReq p);
}
