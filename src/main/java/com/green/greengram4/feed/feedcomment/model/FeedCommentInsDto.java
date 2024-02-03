package com.green.greengram4.feed.feedcomment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class FeedCommentInsDto {
    @JsonIgnore
    private int ifeedComment;
    @Min(1)
    private int ifeed;
    @JsonIgnore
    private int iuser;
    @NotBlank
    private String comment;
}
