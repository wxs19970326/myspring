package com.wxs.mvc.servlet;

import com.wxs.annotation.Autowired;
import com.wxs.annotation.Controller;
import com.wxs.annotation.RequestMapping;
import com.wxs.spring.application.XMLApplBeanFactory;
import com.wxs.utils.ClassScanner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet(urlPatterns = {"/*"},initParams = {@WebInitParam(name = "basePackage",value = "com.wxs.test.controller")})
public class DispatcherServlet extends HttpServlet {

    //存储Controller实例
    private Map<String, Object> controllers = new HashMap<>();

    //存储Method
    private Map<String, Method> methods = new HashMap<>();

    @Override
    public void init() throws ServletException {
        XMLApplBeanFactory beanFactory = new XMLApplBeanFactory("F:\\workspace_IDEA\\mymvc\\src\\com\\wxs\\config.xml");
        ServletConfig servletConfig = this.getServletConfig();
        String basePackage = servletConfig.getInitParameter("basePackage");
        Map<String, Class<?>> cons = ClassScanner.classScanner(basePackage);
        Iterator<String> iterator = cons.keySet().iterator();
        while (iterator.hasNext()) {
            String className = iterator.next();
            Class clazz = cons.get(className);
            //判断这个类是否被Controller注解标记
            if (clazz.isAnnotationPresent(Controller.class)) {
                System.out.println(clazz.getName() + "是控制器");
                //将类管理起来
                try {
                    Object obj = clazz.newInstance();
                    controllers.put(className, obj);
                    String path = "";
                    //判断class是否有RequestMapping注解标记,如果有,将值取出来赋给path
                    if (clazz.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping reqMap = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
                        path = reqMap.value();
                    }
                    //只拿公用方法
                    Method[] ms = clazz.getMethods();
                    for (Method method : ms) {
                        //判断该类中的方法是否有RequestMapping注解标记,如果没有进入下一轮循环
                        if (!method.isAnnotationPresent(RequestMapping.class)) {
                            continue;
                        }
                        //如果有,将该方法放入map集合中,key为访问路径,value为该方法的Method实例
                        methods.put(path + method.getAnnotation(RequestMapping.class).value(), method);
                    }

                    //自动注入
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Autowired.class)) {
                            Class<?> fieldClass = field.getType();
                            Map<String, Object> compoBeans = beanFactory.getBeans();
                            Iterator<String> beanItr = compoBeans.keySet().iterator();
                            while (beanItr.hasNext()) {
                                Object beanObj = compoBeans.get(beanItr.next());
                                if (beanObj.getClass() == fieldClass) {
                                    field.setAccessible(true);
                                    field.set(obj, beanObj);
                                }
                            }
                        }
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        //获取虚拟路径
        String contextPath = req.getContextPath();
        String mappingPath = uri.substring(uri.indexOf(contextPath) + contextPath.length());
        Method method = methods.get(mappingPath);
        try {
            if (method != null) {
                Object controller = controllers.get(method.getDeclaringClass().getName());
                method.invoke(controller);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
