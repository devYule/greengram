package com.green.greengram4.common.my;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @GetMapping("/test")
    public MyTestModel testMethod() {
        MyTestModel myTestModel = new MyTestModel();
        MyTestResult myTestResult = new MyTestResult();
        MyTestResult myTestResult2 = new MyTestResult();
        List<MyTestResult> myTestResults = new ArrayList<>();
        myTestResults.add(myTestResult);
        myTestResults.add(myTestResult2);
        myTestModel.setTestResults(myTestResults);
        myTestModel.setA("A");
        myTestModel.setB("B");
        myTestModel.setC("C");
        return myTestModel;
    }
}
