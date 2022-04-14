package com.kdt.progmrs.kdt.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {


//    @Pointcut("execution(public * com.kdt.progmrs.kdt..*.*(..))")
//    public void servicePublicMethodPointCut() {
//    }


    //    @Around("com.kdt.progmrs.kdt.aop.CommonPointCut.servicePublicMethodPointCut()")           //클래스로 모듈화시켜서 거기있는 메소드 지정해서
    @Around("@annotation(com.kdt.progmrs.kdt.aop.TrackTime)")                   //어노테이션 붙은거만
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("before method called -> {}", joinPoint.getSignature().toString());
        Object result = joinPoint.proceed();
        log.info("after method called -> {}", joinPoint.getSignature().toString());
        return result;
    }
}
