package com.green.greengram4.feed;

import com.green.greengram4.feed.model.FeedFavDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedFavMapperTest {

    @Autowired
    FeedFavMapper mapper;

    FeedFavDto feedFavDto;
    FeedFavDto feedFavDto2;

    @BeforeEach
    void beforeEach() {
        this.feedFavDto = new FeedFavDto();
        this.feedFavDto.setIfeed(11);
        this.feedFavDto.setIuser(1);
        this.feedFavDto2 = new FeedFavDto();
        this.feedFavDto2.setIuser(5);
        this.feedFavDto2.setIfeed(14);
    }

    @Test
    void insertAndDeleteFeedFav() {
        assertEquals(1, mapper.insertFeedFav(this.feedFavDto));
        assertEquals(1, mapper.selFeedFav(this.feedFavDto));
        assertEquals(1, mapper.deleteFeedFav(this.feedFavDto));
        assertEquals(0, mapper.selFeedFav(this.feedFavDto));

        assertEquals(1, mapper.insertFeedFav(this.feedFavDto2));
        assertEquals(1, mapper.selFeedFav(this.feedFavDto2));
        assertEquals(1, mapper.deleteFeedFav(this.feedFavDto2));
        assertEquals(0, mapper.selFeedFav(this.feedFavDto2));

    }

    @Test
    void deleteTest(){
        assertEquals(1, mapper.deleteFeedFav(new FeedFavDto(5, 99)));
        assertEquals(0, mapper.deleteFeedFav(new FeedFavDto(5, 99)));
        assertEquals(0, mapper.selFeedFav(new FeedFavDto(5, 99)));
    }

    @Test
    void delFeedFavAll() {
        assertEquals(2, mapper.delFeedFavAll(100));
        assertNull(mapper.selFeedFavV2(new FeedFavDto(0, 100)));
    }
}