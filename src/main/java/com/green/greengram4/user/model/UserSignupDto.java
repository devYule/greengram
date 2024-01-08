package com.green.greengram4.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSignupDto {
    @Schema(title = "유저 id")
    private String uid;
    private String upw;
    private String nm;
    private String pic;
}
