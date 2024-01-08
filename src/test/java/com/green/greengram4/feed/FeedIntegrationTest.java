package com.green.greengram4.feed;

import com.green.greengram4.BaseIntegrationTest;
import com.green.greengram4.common.ResVo;
import com.green.greengram4.feed.model.FeedDelDto;
import com.green.greengram4.feed.model.FeedInsDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
public class FeedIntegrationTest extends BaseIntegrationTest {

    @Test
//    @Rollback(false)
    void postFeed() throws Exception {

        FeedInsDto dto = new FeedInsDto();
        dto.setIuser(1);
        dto.setContents("통합 테스트 작업 중");
        dto.setLocation("그린컴퓨터학원");
        List<String> pics = new ArrayList<>();
        dto.setPics(pics);
        pics.add("https://www.google.com/url?sa=i&url=https%3A%2F%2Fnamu.wiki%2Fw%2F%25EC%2582%25AC%25EC%25A7%2584&psig=AOvVaw2ABmxCAd18r30m2wDo8cQK&ust=1704250676648000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCIjSkNPavYMDFQAAAAAdAAAAABAE");
        pics.add("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.travie.com%2Fnews%2FarticleView.html%3Fidxno%3D19975&psig=AOvVaw2ABmxCAd18r30m2wDo8cQK&ust=1704250676648000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCIjSkNPavYMDFQAAAAAdAAAAABAJ");


        MvcResult mr = mvc.perform(
                        MockMvcRequestBuilders.post("/api/feed")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andReturn();

        String result = mr.getResponse().getContentAsString();

        // parsing myself
        ResVo resultResVo = new ResVo(Integer.parseInt(result.substring(result.indexOf(":") + 1, result.length() - 1)));
        log.info("resultResVo = {}", resultResVo);

        // use json
        ResVo resultResVo2 = objectMapper.readValue(result, ResVo.class);
        log.info("resultResVo2 = {}", resultResVo2);

        Assertions.assertEquals(resultResVo.getResult(), resultResVo2.getResult());
        Assertions.assertTrue(resultResVo2.getResult() > 0);
    }

    @Test
//    @Rollback(false)
    void delFeed() throws Exception {
        FeedDelDto feedDelDto = new FeedDelDto();
        feedDelDto.setIfeed(123);
        feedDelDto.setIuser(1);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("ifeed", String.valueOf(feedDelDto.getIfeed()));
        params.add("iuser", String.valueOf(feedDelDto.getIuser()));

        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.delete("/api/feed")
                        .params(params)
        ).andDo(print()).andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        log.info("mvcResult.getResponse().getContentAsString() = {}", result);
        ResVo resVoResult = objectMapper.readValue(result, ResVo.class);

        Assertions.assertEquals(resVoResult.getResult(), 1);

    }
}