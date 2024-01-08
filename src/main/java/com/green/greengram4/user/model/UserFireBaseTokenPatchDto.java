package com.green.greengram4.user.model;

import lombok.Data;

@Data
public class UserFireBaseTokenPatchDto {
    private int iuser;
    private String firebaseToken;
}
