package org.example.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextLocal {

    public static ApplicationContext getApplicationContext() {
        return new AnnotationConfigApplicationContext(ConfigApp.class);
    }
}
