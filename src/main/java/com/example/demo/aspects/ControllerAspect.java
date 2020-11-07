package com.example.demo.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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

    @Around("pointCutOnAnyEndPoint()")
    public Object adviceOnPointCutOnAnyEndPoint(ProceedingJoinPoint jp) throws Throwable {
        Long start = System.currentTimeMillis();
        Object result = jp.proceed();
        Long finish = System.currentTimeMillis();
        logger.info(String.format("A call to the endpoint %s was made and it took %d seconds", jp.getSignature(),finish-start));
        return result;
    }
}
