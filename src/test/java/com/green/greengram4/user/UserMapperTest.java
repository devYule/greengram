package com.green.greengram4.user;

import com.green.greengram4.user.model.UserInfoSelDto;
import com.green.greengram4.user.model.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {

    @Autowired
    UserMapper mapper;


    @Test
    void selUserInfo() {
        UserInfoSelDto userInfoSelDto = new UserInfoSelDto();
        userInfoSelDto.setLoginedIuser(1);
        userInfoSelDto.setTargetIuser(2);
        UserInfoVo userInfoVo = mapper.selUserInfo(userInfoSelDto);
        log.info("userInfoVo.getFollowing() = {}", userInfoVo.getFollowing());



    }
}