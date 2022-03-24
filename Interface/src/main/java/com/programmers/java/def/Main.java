package com.programmers.java.def;

interface MyInterface {
    void method1();

    default void method2(){
        System.out.println("hello 구현");
    }

}

public class Main implements MyInterface{
    public static void main(String[] args) {
        System.out.println("hw");
        Main main = new Main();
        main.method2();
    }

    @Override
    public void method1() {
        throw new RuntimeException();

    }
}
