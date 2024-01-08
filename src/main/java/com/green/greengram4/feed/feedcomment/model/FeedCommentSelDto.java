package com.green.greengram4.feed.feedcomment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedCommentSelDto {
    private int ifeed;
    private int startIdx;
    private int rowCount;
}
