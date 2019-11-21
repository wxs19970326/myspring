package com.wxs.test;


import com.wxs.annotation.Component;

@Component
public class Person {

    private String name;

    public String getName() {
        return name;
    }


    public void person() {
        System.out.println("我是person类");
    }
}
