package com.green.greengram4.common.my;

import lombok.Data;

import java.util.List;

@Data
public class MyTestModel {
    private List<MyTestResult> testResults;
    private String a;
    private String b;
    private String c;

}
