package com.wxs;

import com.wxs.spring.application.XMLApplBeanFactory;
import com.wxs.test.Person;

public class Test {

    public static void main(String[] args) {
        XMLApplBeanFactory app = new XMLApplBeanFactory("F:\\workspace_IDEA\\mymvc\\src\\com\\wxs\\config.xml");

        Person person = (Person) app.getBean("Person");


        System.out.println("person的成员变量user " + person);

    }

}
