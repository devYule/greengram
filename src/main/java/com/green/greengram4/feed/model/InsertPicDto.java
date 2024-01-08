package com.green.greengram4.feed.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InsertPicDto {
    private int ifeed;
    private List<String> pics;
}
