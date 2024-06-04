package com.green.greengram.feedFavorite;

import com.green.greengram.feedFavorite.model.FeedFavoriteEntity;
import com.green.greengram.feedFavorite.model.FeedFavoriteToggleReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@ActiveProfiles("tdd")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedFavoriteMapperTest {

    @Autowired
    private FeedFavoriteMapper mapper;

    @Test
    @DisplayName("좋아요 select 테스트")
    void selFeedFavorite() {

        //전수 조사
        FeedFavoriteToggleReq req = new FeedFavoriteToggleReq();
        req.setFeedId(0);
        req.setUserId(0);
        List<FeedFavoriteEntity> list1 = mapper.selFeedFavorite(req);
        assertEquals(20, list1.size(), "리스트 row수가 다름");

        assertEquals(1, list1.get(0).getFeedId(), "1. 1번째 feedId 값이 다름");
        assertEquals(4, list1.get(0).getUserId(), "1. 1번째 userId 값이 다름");
        assertEquals("2024-05-16 13:00:13", list1.get(0).getCreatedAt(), "1. 1번째 createdAt 값이 다름");

        assertEquals(10, list1.get(9).getFeedId(), "2. 10번째 feedId 값이 다름");
        assertEquals(3, list1.get(9).getUserId(), "2. 10번째 userId 값이 다름");
        assertEquals("2024-05-16 13:11:18", list1.get(9).getCreatedAt(), "2. 10번째 createdAt 값이 다름");

        assertEquals(20, list1.get(19).getFeedId(), "3. 20번째 feedId 값이 다름");
        assertEquals(4, list1.get(19).getUserId(), "3. 20번째 userId 값이 다름");
        assertEquals("2024-05-16 13:11:18", list1.get(19).getCreatedAt(), "3. 20번째 createdAt 값이 다름");

        //feed id 에 따른 값 조사
        req.setFeedId(1);
        List<FeedFavoriteEntity> list2 = mapper.selFeedFavorite(req);
        assertEquals(1, list2.size(), "리스트가 여러개 불러와짐");
        assertEquals(1, list2.get(0).getFeedId(), "1. 1번째 feedId 값이 다름");
        assertEquals(4, list2.get(0).getUserId(), "1. 1번째 userId 값이 다름");
        assertEquals("2024-05-16 13:00:13", list2.get(0).getCreatedAt(), "1. 1번째 createdAt 값이 다름");

        req.setFeedId(2);
        List<FeedFavoriteEntity> list3 = mapper.selFeedFavorite(req);
        assertEquals(1, list3.size(), "리스트가 여러개 불러와짐");
        assertEquals(2, list3.get(0).getFeedId(), "2. 1번째 feedId 값이 다름");
        assertEquals(1, list3.get(0).getUserId(), "2. 1번째 userId 값이 다름");
        assertEquals("2024-05-16 11:57:26", list3.get(0).getCreatedAt(), "2. 1번째 createdAt 값이 다름");

        //user id 에 따른 값 조사
        req.setFeedId(0);
        req.setUserId(1);
        List<FeedFavoriteEntity> list4 = mapper.selFeedFavorite(req);
        assertEquals(4, list4.size(), "리스트 사이즈가 다름");
        assertEquals(2, list4.get(0).getFeedId(), "1. 리스트 값의 feedId가 다름");
        assertEquals(1, list4.get(0).getUserId(), "1. 리스트 값의 userId가 다름");
        assertEquals("2024-05-16 11:57:26", list4.get(0).getCreatedAt(), "1. 리스트의 created_at이 다름");

        assertEquals(7, list4.get(1).getFeedId(), "2. 리스트 값의 feedId가 다름");
        assertEquals(1, list4.get(1).getUserId(), "2. 리스트 값의 userId가 다름");
        assertEquals("2024-05-16 13:11:18", list4.get(1).getCreatedAt(), "2. 리스트의 created_at이 다름");

        assertEquals(18, list4.get(3).getFeedId(), "3. 리스트 값의 feedId가 다름");
        assertEquals(1, list4.get(3).getUserId(), "3. 리스트 값의 userId가 다름");
        assertEquals("2024-05-16 13:11:18", list4.get(3).getCreatedAt(), "3. 리스트의 created_at이 다름");


        req.setUserId(2);
        List<FeedFavoriteEntity> list5 = mapper.selFeedFavorite(req);
        assertEquals(6, list5.size(), "리스트 사이즈가 다름");
        assertEquals(4, list5.get(0).getFeedId(), "1. 리스트 값의 feedId가 다름");
        assertEquals(2, list5.get(0).getUserId(), "1. 리스트 값의 userId가 다름");
        assertEquals("2024-05-21 09:51:50", list5.get(0).getCreatedAt(), "1. 리스트의 created_at이 다름");

        assertEquals(14, list5.get(3).getFeedId(), "2. 리스트 값의 feedId가 다름");
        assertEquals(2, list5.get(3).getUserId(), "2. 리스트 값의 userId가 다름");
        assertEquals("2024-05-16 13:11:18", list5.get(3).getCreatedAt(), "2. 리스트의 created_at이 다름");

        assertEquals(19, list5.get(5).getFeedId(), "3. 리스트 값의 feedId가 다름");
        assertEquals(2, list5.get(5).getUserId(), "3. 리스트 값의 userId가 다름");
        assertEquals("2024-05-16 13:11:18", list5.get(5).getCreatedAt(), "3. 리스트의 created_at이 다름");

        req.setFeedId(21);
        req.setUserId(6);
        List<FeedFavoriteEntity> list6 = mapper.selFeedFavorite(req);
        assertEquals(0, list6.size(), "없어야 하는데 있는 데이터");

    }

    @Test
    @DisplayName("좋아요 delete 테스트")
    void delFavorite() {
    }

    @Test
    @DisplayName("좋아요 insert 테스트")
    void insFavorite() {
    }
}