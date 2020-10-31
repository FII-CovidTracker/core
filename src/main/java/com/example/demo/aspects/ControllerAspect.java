package com.example.demo.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class ControllerAspect {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Pointcut("execution(* com.example.demo.controllers.*.*(..))")
    public void pointCutOnAnyEndPoint() {

    }

    @Before("pointCutOnAnyEndPoint()")
    public void adviceOnPointCutOnAnyEndPoint(JoinPoint jp) {
        logger.info(String.format("A call to the endpoint %s will be handled", jp.getSignature()));
    }
}
