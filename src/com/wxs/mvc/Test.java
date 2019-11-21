package com.wxs.mvc;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class Test {

    public static void main(String[] args) throws IOException {
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources("com/wxs/mvc");
        while (dirs.hasMoreElements()) {
//            URL url = dirs.nextElement();
            System.out.println(dirs.nextElement());
        }
//        ClassScanner.classScanner("com.wxs");
//        String rootPath = Thread.currentThread().getContextClassLoader().getResource("com/wxs/controller").getPath();
//        System.out.println(rootPath);
    }


}
