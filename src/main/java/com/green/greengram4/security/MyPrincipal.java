package com.green.greengram4.security;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MyPrincipal {

    private int iuser;

    @Builder.Default
    private List<String> roles = new ArrayList<>();

}
