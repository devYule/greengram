package com.green.greengram4.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPicPatchDto {
    @JsonIgnore
    private int iuser;
    private String pic;
}
