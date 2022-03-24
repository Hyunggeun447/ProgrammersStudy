package com.programmers.java.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main2 {
    public static void main(String[] args) {
        /*List<Integer> integers1 = Arrays.asList(1, 2, 3, 4);
        Stream<Integer> stream = Arrays.asList(1, 2, 3, 4).stream();
        IntStream stream1 = Arrays.stream(new int[]{1, 2, 3});
        Stream<Integer> boxed = Arrays.stream(new int[]{1, 2, 3}).boxed();
        List<Integer> collect = Arrays.stream(new int[]{1, 2, 3}).boxed().collect(Collectors.toList());
        Integer[] integers = Arrays.stream(new int[]{1, 2, 3}).boxed().toArray(Integer[]::new);*/
/*
        Random random = new Random();
        Stream.generate(random::nextInt)
                .limit(10)
                .forEach(System.out::println);
*/
        Stream.iterate(0, i -> i + 2)
                .limit(20)
                .forEach(System.out::println);

    }
}
