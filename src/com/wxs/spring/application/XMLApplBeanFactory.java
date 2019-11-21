package com.wxs.spring.application;

import com.wxs.annotation.AutowiredBeanFactory;
import com.wxs.annotation.ComponentBeanFactory;
import com.wxs.spring.scanner.HandlerMapping;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * mySpring容器
 */
public class XMLApplBeanFactory {

    /**
     * 存放对象的集合
     */
    private Map<String, Object> bean = new HashMap<>();

    public XMLApplBeanFactory(String xmlPath){
        createAllBean(xmlPath, bean);
    }

    /**
     * 调用为Component注解服务的类  为所有有Component注解标记的类创建对象并放入map集合
     * @param path 需要扫描的包的路径
     * @param map 存放对象的集合
     */
    private void createAllBean(String path, Map<String, Object> map) {
        //调用类扫描器扫描该包下的所有的类
        Map<String,Class<?>> classMap = HandlerMapping.handlerClass(path);
        ComponentBeanFactory.creatComponentBean(classMap, map);
        //调用自动注入的方法
        try {
            AutowiredBeanFactory.autowriedField(classMap,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从spring容器中获取对象的方法
     * @param objName 对象名
     * @return 对象
     */
    public Object getBean(String objName) {
        Object object = null;
        Set<String> stringSet = bean.keySet();
        Iterator<String> ite = stringSet.iterator();
        while (ite.hasNext()) {
            String name = ite.next();
            if (name.equals(objName)) {
                object = bean.get(name);
            }
        }
        return object;
    }

    public Map<String, Object> getBeans() {
        return bean;
    }
}
