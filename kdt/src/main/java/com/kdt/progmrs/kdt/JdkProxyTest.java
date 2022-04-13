package com.kdt.progmrs.kdt;


import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class JdkProxyTest {

    public static void main(String[] args) {
        Calculator calculator = new CalculatorImpl();

        Calculator proxyInstance = (Calculator) Proxy.newProxyInstance(
                LoggingInvocationHandler.class.getClassLoader(),
                new Class[]{Calculator.class},
                new LoggingInvocationHandler(calculator));

        int result = proxyInstance.add(1, 2);
        log.info("add {}", result);

    }
}

class CalculatorImpl implements Calculator {

    @Override
    public int add(int a, int b) {
        return a+b;
    }
}

interface Calculator {
    int add(int a, int b);
}

@Slf4j
class LoggingInvocationHandler implements InvocationHandler {

    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("{} <-> executed", method.getName());
        return method.invoke(target, args);
    }

}
