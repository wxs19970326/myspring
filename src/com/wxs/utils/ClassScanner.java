package com.wxs.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 用来扫描指定包下的类
 */
public class ClassScanner {

    /**
     * 服务器下扫描器
     * 扫描某个包下所有的类
     * @param basePath  指定的包路径例如 com.wxs.controller
     * @return Map<String, Class<?>> ----> Map<类名,类的class实例>
     */
    public static Map<String, Class<?>> classScanner(String basePath) {
        Map<String, Class<?>> result = new HashMap<>();
        String filePath = basePath.replace(".", "/");
        System.out.println(filePath);
        try {
            //用当前线程的类加载器获取传来的包路径对应的真实的电脑的绝对路径
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(filePath);
            String rootPath = Thread.currentThread().getContextClassLoader().getResource(filePath).getPath();
            if (rootPath != null) {
                rootPath = rootPath.substring(rootPath.lastIndexOf(filePath));
            }
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                System.out.println(url.getPath());
                if (url.getProtocol().equals("file")) {
                    File file = new File(url.getPath().substring(1));
                    scannerFile(file, rootPath, result);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 非服务器下的包扫描器
     * @param basePath
     * @return
     */
    public static Map<String, Class<?>> classScannerSpring(String basePath) {
        Map<String, Class<?>> result = new HashMap<>();
        String filePath = basePath.replace(".", "/");
        System.out.println(filePath);
        try {
            //用当前线程的类加载器获取传来的包路径对应的真实的电脑的绝对路径
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(filePath);

            ////斜线斜线
            String rootPath = Thread.currentThread().getContextClassLoader().getResource(filePath).getPath() + "/";
            if (rootPath != null) {
                rootPath = rootPath.substring(rootPath.lastIndexOf(filePath));
            }
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                System.out.println(url.getPath());
                // url.getProtocol() 获取当前路径的协议  枚举类型的URL存的都是路径的文件协议
                if ("file".equals(url.getProtocol())) {
                    File file = new File(url.getPath().substring(1));
                    scannerFile(file, rootPath, result);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void scannerFile(File folder, String rootPath, Map<String, Class<?>> classes) {
        //拿到folder这个文件夹下的所有文件对象
        File[] files = folder.listFiles();
        for (int i = 0; files != null&&i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                scannerFile(file, rootPath + file.getName() + "/", classes);
            } else {
                String path = file.getAbsolutePath();
                if (path.endsWith(".class")) {
                    //将路径中的\替换成/
                    path = path.replace("\\", "/");
                    String className = rootPath + path.substring(path.lastIndexOf("/") + 1, path.indexOf(".class"));
                    className = className.replace("/", ".");
                    try {
                        classes.put(className, Class.forName(className));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
