package com.green.greengram4.feed;

import com.green.greengram4.feed.feedcomment.FeedCommentMapper;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelDto;
import com.green.greengram4.feed.feedcomment.model.FeedCommentSelVo;
import com.green.greengram4.feed.model.FeedInsDto;
import com.green.greengram4.feed.model.FeedSelResultVo;
import com.green.greengram4.feed.model.FeedSelectDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// 슬라이스 테스트시 사용. -> 빈등록을 할것만 메모리에 올림.
// @SpringBootTest 는 모두 객체화
@ExtendWith(SpringExtension.class)
@Import({FeedService.class})
class FeedServiceTest {

    // DI 를 위해 @MockBean 사용 (실제 객체는 아니고, 가짜 객체를 만들어서 DI 시킴)
    @MockBean
    private FeedMapper mapper;
    @MockBean
    private FeedPicsMapper picsMapper;
    @MockBean
    private FeedFavMapper favMapper;
    @MockBean
    private FeedCommentMapper commentMapper;

    @Autowired
    FeedService service;


    @Test
    void insertFeed() {

        when(mapper.insertFeed(any())).thenReturn(1);
        when(picsMapper.insertPic(any())).thenReturn(1);
        FeedInsDto feedInsDto = new FeedInsDto();
//        assertEquals(0, service.insertFeed(feedInsDto).getResult());

        // service 에서 호출했는지 여부 체크
        verify(mapper).insertFeed(any());
        verify(picsMapper).insertPic(any());
    }

    @Test
    void getFeeds() {
        FeedSelResultVo feedSelResultVo1 = new FeedSelResultVo();
        feedSelResultVo1.setIfeed(1);
        feedSelResultVo1.setContents("1번");
        FeedSelResultVo feedSelResultVo2 = new FeedSelResultVo();
        feedSelResultVo2.setIfeed(2);
        feedSelResultVo2.setContents("2번");
        List<FeedSelResultVo> list = new ArrayList<>();

        list.add(feedSelResultVo1);
        list.add(feedSelResultVo2);
        List<String> pics = new ArrayList<>();
        pics.add("pic1");
        pics.add("pic2");
        List<String> pics2 = new ArrayList<>();
        pics2.add("사진 1");
        pics2.add("사진 2");

        FeedCommentSelVo feedCommentSelVo1 = new FeedCommentSelVo();
        feedCommentSelVo1.setIfeedComment(1);
        feedCommentSelVo1.setComment("1번 댓글");
        FeedCommentSelVo feedCommentSelVo2 = new FeedCommentSelVo();
        feedCommentSelVo2.setIfeedComment(2);
        feedCommentSelVo2.setComment("2번 댓글");
        List<FeedCommentSelVo> comments = new ArrayList<>();
        comments.add(feedCommentSelVo1);
        comments.add(feedCommentSelVo2);

        FeedCommentSelVo feedCommentSelVo12 = new FeedCommentSelVo();
        feedCommentSelVo12.setIfeedComment(1);
        feedCommentSelVo12.setComment("1번 댓글");
        FeedCommentSelVo feedCommentSelVo22 = new FeedCommentSelVo();
        feedCommentSelVo22.setIfeedComment(2);
        feedCommentSelVo22.setComment("2번 댓글");
        List<FeedCommentSelVo> comments2 = new ArrayList<>();
        comments.add(feedCommentSelVo1);
        comments.add(feedCommentSelVo2);


        when(mapper.getFeeds(any())).thenReturn(list);
        when(picsMapper.getEachPics(1)).thenReturn(pics);
        when(picsMapper.getEachPics(2)).thenReturn(pics2);
//        when(commentMapper.selFeedCommentAll(any())).thenReturn(comments);


        List<FeedSelResultVo> result = service.getFeeds(any());

        for (FeedSelResultVo f : list) {
            if (f.getIfeed() == 1) {
                assertEquals(f.getPics(), pics);
            } else {
                assertEquals(f.getPics(), pics2);
            }
        }

        List<List<String>> picsList = new ArrayList<>();
        picsList.add(pics);
        picsList.add(pics2);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(list.get(i).getPics().get(j), picsList.get(i).get(j));
            }
        }

//        List<String>[] test = new List[]{pics, pics2};
        List<String>[] test = new List[2];
        test[0] = pics;
        test[1] = pics2;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(list.get(i).getPics().get(j), test[i].get(j));
            }
        }

        for (int i = 0; i < result.size(); i++) {
            if (i == 0) {
                assertEquals(result.get(i).getPics(), pics);
            } else {
                assertEquals(result.get(i).getPics(), pics2);
            }
            assertEquals(result.get(i).getComments(), comments);
        }


//        when(commentMapper.selFeedCommentAll(any())).thenReturn(comments2);
        service.getFeeds(any());
        for (int i = 0; i < result.size(); i++) {

            assertEquals(result.get(i).getComments(), comments2);
        }

        list.remove(1);
        assertEquals(result.size(), list.size());
        assertEquals(1, list.size());
        assertEquals(1, result.size());

        //
        //
        //
        //
        //

        FeedCommentSelVo feedCommentSelVo3 = new FeedCommentSelVo();
        feedCommentSelVo3.setIfeedComment(1);
        feedCommentSelVo3.setComment("1번 댓글");
        FeedCommentSelVo feedCommentSelVo32 = new FeedCommentSelVo();
        feedCommentSelVo32.setIfeedComment(2);
        feedCommentSelVo32.setComment("2번 댓글");
        FeedCommentSelVo feedCommentSelVo33 = new FeedCommentSelVo();
        feedCommentSelVo32.setIfeedComment(3);
        feedCommentSelVo32.setComment("3번 댓글");
        FeedCommentSelVo feedCommentSelVo34 = new FeedCommentSelVo();
        feedCommentSelVo32.setIfeedComment(4);
        feedCommentSelVo32.setComment("4번 댓글");
        List<FeedCommentSelVo> comments3 = new ArrayList<>();
        comments3.add(feedCommentSelVo3);
        comments3.add(feedCommentSelVo32);
        comments3.add(feedCommentSelVo33);
        comments3.add(feedCommentSelVo34);
//        when(commentMapper.selFeedCommentAll(any())).thenReturn(comments3);

        List<FeedSelResultVo> feeds = service.getFeeds(new FeedSelectDto());
        assertEquals(feeds, list);
        for (FeedSelResultVo obj : list) {
            assertEquals(3, obj.getComments().size());
            assertEquals(obj.getComments(), comments3);
            assertEquals(obj.getIsMoreComment(), 1);
        }

        feedSelResultVo1 = new FeedSelResultVo();
        feedSelResultVo1.setIfeed(1);
        feedSelResultVo1.setContents("1번");
        feedSelResultVo2 = new FeedSelResultVo();
        feedSelResultVo2.setIfeed(2);
        feedSelResultVo2.setContents("2번");
        list = new ArrayList<>();
        comments3.clear();

        comments3.add(feedCommentSelVo3);
        comments3.add(feedCommentSelVo32);

        List<FeedCommentSelVo> cmt1 = new ArrayList<>();
        FeedCommentSelVo feedCommentSelVo1_1 = new FeedCommentSelVo();
        feedCommentSelVo1_1.setIfeedComment(1);
        feedCommentSelVo1_1.setComment("first");
        FeedCommentSelVo feedCommentSelVo1_2 = new FeedCommentSelVo();
        feedCommentSelVo1_2.setIfeedComment(2);
        feedCommentSelVo1_2.setComment("second");
        List<FeedCommentSelVo> cmt2 = new ArrayList<>();
        FeedCommentSelVo feedCommentSelVo2_1 = new FeedCommentSelVo();
        feedCommentSelVo2_1.setIfeedComment(1);
        feedCommentSelVo2_1.setComment("일번");
        FeedCommentSelVo feedCommentSelVo2_2 = new FeedCommentSelVo();
        feedCommentSelVo2_2.setIfeedComment(2);
        feedCommentSelVo2_2.setComment("이번");
        FeedCommentSelVo feedCommentSelVo2_3 = new FeedCommentSelVo();
        feedCommentSelVo2_3.setIfeedComment(3);
        feedCommentSelVo2_3.setComment("삼번");
        FeedCommentSelVo feedCommentSelVo2_4 = new FeedCommentSelVo();
        feedCommentSelVo2_4.setIfeedComment(4);
        feedCommentSelVo2_4.setComment("사번");


//        when(commentMapper.selFeedCommentAll(any())).thenReturn(comments3);
        when(mapper.getFeeds(any())).thenReturn(list);

        List<FeedSelResultVo> feeds2 = service.getFeeds(new FeedSelectDto());
        assertEquals(feeds2, list);
        for (FeedSelResultVo obj : list) {
            assertEquals(2, obj.getComments().size());
            assertEquals(obj.getComments(), comments3);
            assertEquals(obj.getIsMoreComment(), 0);
        }

        //

//        for (FeedSelResultVo feedSelResultVo : list) {
//            when(mapper.getFeeds(any())).thenReturn(list);
//            if (feedSelResultVo.getIfeed() == 1) {
//                when(commentMapper.selFeedCommentAll(any())).thenReturn(cmt1);
//                service.getFeeds(new FeedSelectDto());
//            }
//            if (feedSelResultVo.getIfeed() == 2) {
//                when(commentMapper.selFeedCommentAll(any())).thenReturn(cmt2);
//                service.getFeeds(new FeedSelectDto());
//            }
//        }


        FeedCommentSelDto feedCommentSelDto1 = new FeedCommentSelDto();
        feedCommentSelDto1.setIfeed(1);
        feedCommentSelDto1.setStartIdx(0);
        feedCommentSelDto1.setRowCount(4);

        FeedCommentSelDto feedCommentSelDto2 = new FeedCommentSelDto();
        feedCommentSelDto1.setIfeed(2);
        feedCommentSelDto1.setStartIdx(0);
        feedCommentSelDto1.setRowCount(4);

        when(mapper.getFeeds(any())).thenReturn(list);
        when(commentMapper.selFeedCommentAll(feedCommentSelDto1)).thenReturn(cmt1);
        when(commentMapper.selFeedCommentAll(feedCommentSelDto2)).thenReturn(cmt2);
        service.getFeeds(new FeedSelectDto());

        for (FeedSelResultVo feedSelResultVo : list) {
            if (feedSelResultVo.getIfeed() == 1) {
                assertEquals(feedSelResultVo.getComments(), cmt1);
            }
            if (feedSelResultVo.getIfeed() == 2) {
                assertEquals(1, feedSelResultVo.getIsMoreComment());
                assertEquals(feedSelResultVo.getComments(), cmt2);
                assertEquals(feedSelResultVo.getComments().size(), cmt2.size());
                assertEquals(3, cmt2.size());

            }
        }


    }

    @Test
    void test() {
        FeedSelResultVo feedSelResultVo1 = new FeedSelResultVo();
        feedSelResultVo1.setIfeed(1);
        feedSelResultVo1.setContents("1번");
        FeedSelResultVo feedSelResultVo2 = new FeedSelResultVo();
        feedSelResultVo2.setIfeed(2);
        feedSelResultVo2.setContents("2번");
        List<FeedSelResultVo> list = new ArrayList<>();

        list.add(feedSelResultVo1);
        list.add(feedSelResultVo2);


        List<FeedCommentSelVo> cmt1 = new ArrayList<>();
        FeedCommentSelVo feedCommentSelVo1_1 = new FeedCommentSelVo();
        feedCommentSelVo1_1.setIfeedComment(1);
        feedCommentSelVo1_1.setComment("first");
        FeedCommentSelVo feedCommentSelVo1_2 = new FeedCommentSelVo();
        feedCommentSelVo1_2.setIfeedComment(2);
        feedCommentSelVo1_2.setComment("second");
        cmt1.add(feedCommentSelVo1_1);
        cmt1.add(feedCommentSelVo1_2);

        List<FeedCommentSelVo> cmt2 = new ArrayList<>();
        FeedCommentSelVo feedCommentSelVo2_1 = new FeedCommentSelVo();
        feedCommentSelVo2_1.setIfeedComment(1);
        feedCommentSelVo2_1.setComment("일번");
        FeedCommentSelVo feedCommentSelVo2_2 = new FeedCommentSelVo();
        feedCommentSelVo2_2.setIfeedComment(2);
        feedCommentSelVo2_2.setComment("이번");
        FeedCommentSelVo feedCommentSelVo2_3 = new FeedCommentSelVo();
        feedCommentSelVo2_3.setIfeedComment(3);
        feedCommentSelVo2_3.setComment("삼번");
        FeedCommentSelVo feedCommentSelVo2_4 = new FeedCommentSelVo();
        feedCommentSelVo2_4.setIfeedComment(4);
        feedCommentSelVo2_4.setComment("사번");
        cmt2.add(feedCommentSelVo2_1);
        cmt2.add(feedCommentSelVo2_2);
        cmt2.add(feedCommentSelVo2_3);
        cmt2.add(feedCommentSelVo2_4);

        FeedCommentSelDto feedCommentSelDto1 = new FeedCommentSelDto();
        feedCommentSelDto1.setIfeed(feedSelResultVo1.getIfeed());
        feedCommentSelDto1.setStartIdx(0);
        feedCommentSelDto1.setRowCount(4);

        FeedCommentSelDto feedCommentSelDto2 = new FeedCommentSelDto();
        feedCommentSelDto2.setIfeed(feedSelResultVo2.getIfeed());
        feedCommentSelDto2.setStartIdx(0);
        feedCommentSelDto2.setRowCount(4);


        when(mapper.getFeeds(any())).thenReturn(list);
        when(commentMapper.selFeedCommentAll(feedCommentSelDto1)).thenReturn(cmt1);
        when(commentMapper.selFeedCommentAll(feedCommentSelDto2)).thenReturn(cmt2);
        List<FeedSelResultVo> result = service.getFeeds(new FeedSelectDto());

        assertEquals(result, list);

        for (FeedSelResultVo feedSelResultVo : list) {
            if (feedSelResultVo.getIfeed() == 1) {
                assertEquals(feedSelResultVo.getComments(), cmt1);
                assertEquals(2, cmt1.size());
            }
            if (feedSelResultVo.getIfeed() == 2) {
                assertEquals(1, feedSelResultVo.getIsMoreComment());
                assertEquals(feedSelResultVo.getComments(), cmt2);
                assertEquals(feedSelResultVo.getComments().size(), cmt2.size());
                assertEquals(3, cmt2.size());

            }
        }
    }

    @Test
    void delFeed() {
    }

    @Test
    void toggleFeedFav() {
    }
}