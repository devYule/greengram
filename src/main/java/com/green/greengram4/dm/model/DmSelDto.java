package com.green.greengram4.dm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DmSelDto {
    private int loginedIuser;
    private int page;


    @JsonIgnore
    private int startIdx;
    @JsonIgnore
    private int rowCount;



}
