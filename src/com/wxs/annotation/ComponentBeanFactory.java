package com.wxs.annotation;

import java.util.Iterator;
import java.util.Map;

/**
 * 为Component注解类服务的类
 */
public class ComponentBeanFactory {

    /**
     * 为所有被Component注解标记的类创建对象并放入map集合
     * @param classMap 存放需要扫描的包下的所有类的全限定名
     * @param bean
     */
    public static void creatComponentBean(Map<String,Class<?>> classMap, Map<String, Object> bean) {
        Iterator<String> iterator = classMap.keySet().iterator();
        while (iterator.hasNext()) {
            String className = iterator.next();
            Class clazz = classMap.get(className);
            //判断这个类是否被Component注解标记
            if (clazz.isAnnotationPresent(Component.class)) {
                System.out.println(clazz.getName() + "需要创建对象");
                try {
                    //将类的对象管理起来  键为类名  值为该类的对象
                    bean.put(className.substring(className.lastIndexOf(".")+ 1), clazz.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
