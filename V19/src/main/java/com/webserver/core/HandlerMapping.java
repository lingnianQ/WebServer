package com.webserver.core;

import com.webserver.annotations.Controller;
import com.webserver.annotations.RequestMapping;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sytsnb@gmail.com
 * @Date 2022 2022/8/19 15:48
 */

public class HandlerMapping {
    private static Map<String, Method> mapping = new HashMap<>();

    static {
        initMapping();
    }

    /**
     * 获取method
     * @param s
     * @return
     */
    public static Method getMethod(String s) {
        return mapping.get(s);
    }

    private static void initMapping() {
        try {
            File dir = new File(
                    HandlerMapping.class.getClassLoader().getResource(
                            "./com/webserver/controller"
                    ).toURI()
            );

            File[] subs = dir.listFiles(pathname -> pathname.getName().endsWith(".class"));
            for (File sub : subs) {
                String className = sub.getName().substring(0, sub.getName().indexOf("."));
                Class<?> cls = Class.forName("com.webserver.controller." + className);
                if (cls.isAnnotationPresent(Controller.class)) {
                    Object obj = cls.newInstance();
                    Method[] methods = cls.getDeclaredMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            mapping.put(method.getAnnotation(RequestMapping.class).value(), method);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        System.out.println(mapping);
        mapping.forEach((s, method) -> System.out.println(s + ":" + method));
    }
}
