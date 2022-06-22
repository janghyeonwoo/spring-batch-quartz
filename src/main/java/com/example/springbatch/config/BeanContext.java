package com.example.springbatch.config;

import org.springframework.context.ApplicationContext;

public class BeanContext {
    private static ApplicationContext context;
    public static void init(ApplicationContext context){
        BeanContext.context = context;
    }

    public static <T> T get(Class<T> clazz){
        return context.getBean(clazz);
    }
}
