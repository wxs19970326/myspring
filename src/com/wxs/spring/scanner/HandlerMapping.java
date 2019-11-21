package com.wxs.spring.scanner;

import com.wxs.utils.ClassScanner;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

public class HandlerMapping {

    /**
     * 映射类的方法  将指定包路径下的所有类扫描出来放入集合  键为类的全限定名  值为类的class对象
     * @param path 从xml文件中读取的需要扫描的包路径
     * @return
     */
    public static Map<String, Class<?>> handlerClass(String path) {
        String basePath = null;
        try {
            basePath = readXML(path);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return ClassScanner.classScannerSpring(basePath);
    }

    /**
     * 读取xml文件  该配置文件配置的是需要扫描的包路径
     * @param path 配置文件路径
     * @return 返回包路径
     * @throws DocumentException
     */
    private static String readXML(String path) throws DocumentException {
        File file = new File(path);
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SAXReader reader = new SAXReader();
        Document document = reader.read(fr);
        Element root = document.getRootElement();
        Iterator iterator = root.elementIterator();
        String basePack = "";
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            if (element.getName().equals("context")) {
                basePack = element.elementTextTrim("scanner-path");
            }
        }
        return basePack;
    }
}
