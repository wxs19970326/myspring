package com.wxs.mvc.controller;

import com.wxs.annotation.Controller;
import com.wxs.annotation.RequestMapping;

@Controller
@RequestMapping("/emp")
public class EmpController {

    @RequestMapping("/add")
    public void addEmp() {
        System.out.println("addEmp方法被执行");
    }

    @RequestMapping("/delete")
    public void deleteEmp() {
        System.out.println("deleteEmp方法被执行");
    }

}
