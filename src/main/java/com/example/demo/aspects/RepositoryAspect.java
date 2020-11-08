package com.example.demo.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Aspect
public class RepositoryAspect {
    Logger logger = Logger.getLogger(this.getClass().getName());


}
