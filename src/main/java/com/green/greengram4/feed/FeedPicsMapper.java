package com.green.greengram4.feed;

import com.green.greengram4.feed.model.InsertPicDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedPicsMapper {

    int insertPic(InsertPicDto insertPicDto);
    List<String> getEachPics (int ifeed);

    int delFeedPicsAll(int ifeed);
}
