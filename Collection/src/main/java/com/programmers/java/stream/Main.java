package com.programmers.java.stream;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        long count = Arrays.asList("A", "ASD", "DS", "SFED", "CVDF")
                .stream()
                .map(i -> i.length())
                .filter(i -> i % 2 == 0)
                .count();

        System.out.println("count = " + count);

    }
}
