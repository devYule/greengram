package com.green.greengram4.feed;

import com.green.greengram4.feed.model.InsertPicDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FeedPicsMapperTest {

    @Autowired
    FeedPicsMapper mapper;

    InsertPicDto testObj;
    List<String> pics;

    @BeforeEach
    void beforeEach() {
        this.pics = new ArrayList<>();
        this.pics.add("test1");
        this.pics.add("test2");
        this.testObj = new InsertPicDto(9, pics);
        mapper.delFeedPicsAll(this.testObj.getIfeed());
    }

    @Test
    void insertPic() {

        List<String> eachPics = mapper.getEachPics(this.testObj.getIfeed());
        assertEquals(2, mapper.insertPic(this.testObj));
        List<String> eachPics2 = mapper.getEachPics(this.testObj.getIfeed());
        assertEquals(eachPics.size() + testObj.getPics().size(), eachPics2.size());

        this.pics.remove(1);
        eachPics = mapper.getEachPics(this.testObj.getIfeed());
        assertEquals(1, mapper.insertPic(this.testObj));
        eachPics2 = mapper.getEachPics(this.testObj.getIfeed());
        assertEquals(eachPics.size() + testObj.getPics().size(), eachPics2.size());

        List<String> eachPics1 = mapper.getEachPics(this.testObj.getIfeed());
        boolean check = true;
        for (String pic : this.testObj.getPics()) {
            if(eachPics1.contains(pic)){
                continue;
            }
            check = false;
        }
        Assertions.assertThat(check).isTrue();


    }

    @Test
    void getEachPics() {

        List<String> eachPics = mapper.getEachPics(this.testObj.getIfeed());
        assertEquals(eachPics.size(), mapper.delFeedPicsAll(this.testObj.getIfeed()));
        assertEquals(0, mapper.getEachPics(this.testObj.getIfeed()).size());

    }

    @Test
    void delFeedPicsAll() {

        List<String> eachPics = mapper.getEachPics(this.testObj.getIfeed());
        assertEquals(eachPics.size(), mapper.delFeedPicsAll(this.testObj.getIfeed()));
        mapper.insertPic(this.testObj);
        assertEquals(2, mapper.delFeedPicsAll(this.testObj.getIfeed()));
        assertEquals(0, mapper.getEachPics(this.testObj.getIfeed()).size());


    }
}