package com.green.greengram4.feed;

import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService service;

    @PostMapping
    public ResVo insertFeed(@RequestBody FeedInsDto feedInsDto) {


        return service.insertFeed(feedInsDto);
    }


    @GetMapping
    public List<FeedSelResultVo> getFeeds(FeedSelectDto feedSelectDto) {
        log.info("feedSelectDto = {}", feedSelectDto);

        return service.getFeeds(feedSelectDto);
    }


    @DeleteMapping
    public ResVo delFeed(FeedDelDto feedDelDto) {
        log.info("feedDelDto = {}", feedDelDto);
        return  service.delFeed(feedDelDto);
    }


    @GetMapping("/fav")
    public ResVo toggleFeedFav(FeedFavDto feedFavDto) {
        log.info("feedFavDto = {}", feedFavDto);
        return service.toggleFeedFav(feedFavDto);
    }

}
