package com.green.greengram4.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.MockMvcConfig;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedInsDto;
import com.green.greengram4.feed.model.FeedSelResultVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeedController.class)
@MockMvcConfig
class FeedControllerTest {

    @Autowired
    private MockMvc mvc; // Mock 요청 메시지 전송 객체

    @MockBean
    private FeedService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void insertFeed() throws Exception {
        ResVo result = new ResVo(2);
        when(service.insertFeed(any())).thenReturn(result);
        given(service.insertFeed(any())).willReturn(result);

        FeedInsDto dto = new FeedInsDto();

        mvc.perform(
                        MockMvcRequestBuilders.post("/api/feed")
                                .content(objectMapper.writeValueAsString(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(result)))
                .andDo(print());

        verify(service).insertFeed(any());
    }

    @Test
    void getFeeds() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "1");
        params.add("loginedIuser", "2");

        List<FeedSelResultVo> result = new ArrayList<>();
        result.add(new FeedSelResultVo(1, "테스트", "testLocation", null, 2, "testUser", "testPic", null, 1, null, 0));
        result.add(new FeedSelResultVo(2, "test2", "testLocation2", null, 3, "testUser2", "testPic2", null, 0, null, 0));
//
//        FeedSelectDto feedSelectDto = new FeedSelectDto();
//        feedSelectDto.setPage(1);
//        feedSelectDto.setLoginedIuser(2);
//        when(service.getFeeds(feedSelectDto)).thenReturn(result);

        when(service.getFeeds(any())).thenReturn(result);

        mvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/feed")
                                .params(params)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(result)));

        verify(service).getFeeds(any());

    }

    @Test
    void delFeed() throws Exception {
        ResVo result = new ResVo(100);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ifeed", "1");
        params.add("iuser", "2");

        when(service.delFeed(any())).thenReturn(result);

        mvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/feed")
                                .params(params)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(result)));

        verify(service).delFeed(any());

    }

    @Test
    void toggleFeedFav() {
    }
}