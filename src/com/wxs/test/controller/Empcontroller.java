package com.wxs.test.controller;

import com.wxs.annotation.Autowired;
import com.wxs.annotation.Controller;
import com.wxs.annotation.RequestMapping;
import com.wxs.test.service.EmpService;

@Controller
public class Empcontroller {

    @Autowired
    private EmpService empService;

    @RequestMapping("/add")
    public void addEmp() {
        System.out.println("测试" + empService);
        empService.add();
    }


}
