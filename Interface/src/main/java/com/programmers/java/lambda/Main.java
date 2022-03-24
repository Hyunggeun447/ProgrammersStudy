package com.programmers.java.lambda;

import java.util.function.Consumer;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {

        MySupplier<String> s = () -> "Hello Supply";

        MyMapper<String,Integer> m = String::length;
        MyMapper<Integer, Integer> m2 = i -> i * i;
        MyMapper<Integer, String> m3 = Integer::toHexString;

        MyConsumer<String> c = System.out::println;

//        MyRunnable r = () -> {
//            c.consume(m.map(s.supply()));
//        };
        MyRunnable r = () -> c.consume(m3.map(m2.map(m.map(s.supply()))));

        r.run();



    }
}
