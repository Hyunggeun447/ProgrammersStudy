package com.programmers.java;

public class MyWorld {
    public static void main(String[] args) {

        MyWorld myWorld = new MyWorld();

        String a1 = "hello";
        String b1 = "hello";

        System.out.println(a1 == b1);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }

        System.out.println(sb);

    }

}
