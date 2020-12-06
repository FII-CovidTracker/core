package com.example.demo.aspects;

import com.example.demo.Exceptions.InvalidArgumentException;
import org.apache.tomcat.jni.Proc;
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


    @Pointcut("(execution(* com.example.demo.controllers.UserController.deleteById(..))||" +
            " execution(* com.example.demo.controllers.ArticleController.deleteById(..))||" +
            " execution(* com.example.demo.controllers.ClinicsController.deleteById(..))||" +
            " execution(* com.example.demo.controllers.AuthorityController.deleteById(..))||" +
            " execution(* com.example.demo.controllers.RegionController.deleteById(..))||" +
            "execution(* com.example.demo.controllers.UserController.getById(..))||" +
            "execution(* com.example.demo.controllers.ArticleController.getById(..))||" +
            "execution(* com.example.demo.controllers.ClinicsController.getById(..))||" +
            "execution(* com.example.demo.controllers.AuthorityController.getById(..))||" +
            "execution(* com.example.demo.controllers.RegionController.getById(..))) && args(id))")
    public void getByIdAndDeleteByIdAndId(Long id) {

    }


    @Around("getByIdAndDeleteByIdAndId(id)")
    public Object adviceOngetDeleteByIdAndId(ProceedingJoinPoint pjp, Long id) throws Throwable {
        if (id < 0) {
            logger.info("The id does not exist!");
            throw new InvalidArgumentException(id, "Id");
        } else {
            return pjp.proceed();
        }
    }

    @Pointcut("execution(* com.example.demo.controllers.*.*(..))")
    public void loggingJoinPoint(){

    }
    @Around("loggingJoinPoint()")
    public void adivceOnloggingJoinPoint(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println(pjp.getSignature().getDeclaringTypeName());
        pjp.proceed();
    }


}
