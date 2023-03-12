package com.vose.voseengine;

import org.junit.jupiter.api.Test;

public class Sandbox {
    @Test
    public void test1() {
        System.out.println(Integer.parseInt("23"));
        System.out.println(Integer.parseInt("23/"));
    }

    @Test
    public void test2() {
        String numberStr = "234/";

        System.out.println(numberStr.substring(0, -1));
    }
}
