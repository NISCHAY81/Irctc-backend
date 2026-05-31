package com.substring.irctc.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private  final static Logger logger = LoggerFactory.getLogger(LoggingAspect.class.getName());

    // cross cutting concern
    @Before("execution(* com.substring.irctc.services.impl.TrainServiceImpl.getAllTrains(..))")   // advice
    private void logBeforeMethod(JoinPoint joinPoint) {
        logger.info("Before method execution {}",joinPoint.getSignature().getName() );
        System.out.println("Before method execution");
    }


}
