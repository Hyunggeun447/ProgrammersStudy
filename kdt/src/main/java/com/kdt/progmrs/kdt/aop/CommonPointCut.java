package com.kdt.progmrs.kdt.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * 각 메서드를 지정하고 이걸 Aspect에서 @Around에서 지정해줘서 Aop
 */
public class CommonPointCut {

    @Pointcut("execution(public * com.kdt.progmrs.kdt..*.*(..))")
    public void allPublicMethodPointCut() {
    }

    @Pointcut("execution(public * com.kdt.progmrs.kdt..*Service.*(..))")
    public void servicePublicMethodPointCut() {
    }

    @Pointcut("execution(public * com.kdt.progmrs.kdt..*Repository.*(..))")
    public void repositoryPublicMethodPointCut() {
    }

}
