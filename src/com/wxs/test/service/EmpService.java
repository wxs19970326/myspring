package com.wxs.test.service;

import com.wxs.annotation.Autowired;
import com.wxs.annotation.Component;
import com.wxs.test.Person;

@Component
public class EmpService {

    @Autowired
    private Person person;

    public void add() {
        person.person();
        System.out.println("empservice方法被执行");
    }

}
