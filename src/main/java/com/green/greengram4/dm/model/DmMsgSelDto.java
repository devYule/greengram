package com.green.greengram4.dm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class DmMsgSelDto {
    private int page;
    private int idm;
    @JsonIgnore
    private int startIdx;
    @JsonIgnore
    private int rowCount;


}
