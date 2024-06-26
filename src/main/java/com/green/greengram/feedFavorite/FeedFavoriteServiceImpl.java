package com.green.greengram.feedFavorite;

import com.green.greengram.feedFavorite.intf.FeedFavoriteService;
import com.green.greengram.feedFavorite.model.FeedFavoriteToggleReq;
import com.green.greengram.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeedFavoriteServiceImpl implements FeedFavoriteService {
    private final FeedFavoriteMapper mapper;
    private final AuthenticationFacade authenticationFacade;

    @Transactional
    public int toggleFavorite (FeedFavoriteToggleReq p){
        p.setUserId(authenticationFacade.getLogInUserId());
         if(mapper.delFavorite(p) == 0){
          return mapper.insFavorite(p);
         }
         return 0;
    }
}
