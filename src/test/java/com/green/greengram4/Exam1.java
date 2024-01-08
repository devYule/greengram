package com.green.greengram4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Exam1 {

    MyUtils utils = new MyUtils();

    @Test
    @DisplayName("테스트1")
    void test1() {
        System.out.println("test1");
        int sum = 2 + 2;
        Assertions.assertEquals(4, sum);
        assertThat(sum).isEqualTo(4);
    }

    @Test
    void 테스트2() {
        System.out.println("test2");
        int multi = 2 * 3;
        assertThat(multi).isEqualTo(6);
    }

    @Test
    void test3() {
        System.out.println("test3");
        Assertions.assertEquals(3, MyUtils.sum(1, 2));
        Assertions.assertEquals(2, MyUtils.sum(1, 1));
        Assertions.assertEquals(-2, MyUtils.sum(-3, 1));
    }

    @Test
    void test4() {
        System.out.println("test4");
        Assertions.assertEquals(2, utils.multi(1, 2));
        Assertions.assertEquals(0, utils.multi(3, 0));
        Assertions.assertEquals(0, utils.multi(0, 22));
        Assertions.assertEquals(1, utils.multi(1, 1));
        Assertions.assertEquals(50, utils.multi(5, 10));
        Assertions.assertEquals(-50, utils.multi(-5, 10));
        Assertions.assertEquals(-50, utils.multi(5, -10));
    }


}
