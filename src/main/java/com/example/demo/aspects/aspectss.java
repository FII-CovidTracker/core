package com.example.demo.aspects;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.logging.Logger;

@Aspect
public class aspectss {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Before("execution( * *(..))")
    public void advice() throws Throwable {
//        System.out.println("POINTCUTTTT");
//        return pjp.proceed();
        logger.info("jmen");
    }
}
