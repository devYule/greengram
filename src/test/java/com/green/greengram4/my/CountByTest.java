package com.green.greengram4.my;

import com.green.greengram4.entity.FeedFavIdentity;
import com.green.greengram4.feed.FeedFavRepository;
import com.green.greengram4.feed.FeedRepository;
import com.green.greengram4.user.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CountByTest {

    @Autowired
    FeedFavRepository feedFavRepository;

    @Autowired
    FeedRepository feedRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager em;


    @Test
    void test() {

        long allCount = feedFavRepository.count();
        System.out.println("allCount = " + allCount);

        FeedFavIdentity fe = FeedFavIdentity.builder()
                .iuser(1L)
                .ifeed(20L)
                .build();
//        long byIdentity = feedFavRepository.countById(fe);
//        System.out.println("byIdentity = " + byIdentity);


        int size = feedFavRepository.findByFeedFavIdentity(fe).size();
        System.out.println("size = " + size);

        long countBy = feedFavRepository.countByFeedFavIdentity(fe);
        System.out.println("countBy = " + countBy);


    }

}
