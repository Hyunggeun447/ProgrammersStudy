package com.programmers.java.func;

public class Main2 {
    public static void main(String[] args) {

        MyRunnable r1 = new MyRunnable() {
            @Override
            public void run() {
                System.out.println("Hello MyRunnable 익명");
            }
        };

        //람다식 변환 (필요없는 것 다 제거) 함수형 인터페이스라서 가능
        MyRunnable r2 = () -> System.out.println("Hello MyRunnable 익명");

        MySupply s1 = () -> "Hello World lambda";

        MyRunnable r3 = () -> {
            MySupply s2 = () -> "Hello Hello Hello";
            System.out.println(s2.supply());
        };
        r3.run();



    }
}
