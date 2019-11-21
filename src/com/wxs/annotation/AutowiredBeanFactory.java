package com.wxs.annotation;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class AutowiredBeanFactory {

    public static void autowriedField(Map<String,Class<?>> classMap, Map<String, Object> bean) throws IllegalAccessException {

        Iterator<String> iterator = bean.keySet().iterator();
        while (iterator.hasNext()) {
            Object o = bean.get(iterator.next());
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Class<?> fieldClass = field.getType();
                    Iterator<String> beanItr = bean.keySet().iterator();
                    while (beanItr.hasNext()) {
                        Object beanObj = bean.get(beanItr.next());
                        System.out.println("已有" + beanObj.getClass());
                        System.out.println("需要注入的" + fieldClass);
                        if (beanObj.getClass() == fieldClass) {
                            field.setAccessible(true);
                            field.set(o, beanObj);
                        }
                    }
                }
            }
        }

//        Iterator<String> iterator = classMap.keySet().iterator();
//        while (iterator.hasNext()) {
//            String className = iterator.next();
//            Class clazz = classMap.get(className);
//            //判断这个类是否被Component注解标记
//            if (clazz.isAnnotationPresent(Component.class)) {
//                try {
//                    //将类的对象管理起来  键为类名  值为该类的对象
//                    Object obj = clazz.newInstance();
//                    Field[] fields = clazz.getDeclaredFields();
//                    for (Field field : fields) {
//                        System.out.println("================");
//                        if (field.isAnnotationPresent(Autowired.class)) {
//                            Class<?> fieldClass = field.getType();
//                            Iterator<String> beanItr = bean.keySet().iterator();
//                            while (beanItr.hasNext()) {
//                                Object beanObj = bean.get(beanItr.next());
//                                System.out.println("已有" + beanObj.getClass());
//                                System.out.println("需要注入的" + fieldClass);
//                                if (beanObj.getClass() == fieldClass) {
//                                    field.setAccessible(true);
//                                    field.set(obj, beanObj);
//                                }
//                            }
//                        }
//                    }
////                    bean.put(className.substring(className.lastIndexOf(".")+ 1), obj);
//                } catch (InstantiationException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

}
