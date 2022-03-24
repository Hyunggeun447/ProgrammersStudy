package com.programmers.java.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;

public class Main3 {
    public static void main(String[] args) {
//        Random random = new Random();
//        long count = Stream.generate(() -> random.nextInt(1, 7))
//                .limit(100)
//                .filter(i -> i == 6)
//                .count();
//
//        System.out.println("count = " + count);
//        double l = (double) count / 100;
//        System.out.println("l = " + l);
//
        /*Random random = new Random();
        int[] ints = Stream.generate(() -> random.nextInt(1, 11))
                .distinct()
                .limit(10)
                .mapToInt(i -> i)
                .toArray();

        System.out.println("ints = " + Arrays.toString(ints));
*/
        Random random = new Random();

        int[] ints = Stream.generate(() -> random.nextInt(201))
                .distinct()
                .limit(5)
                .sorted(Comparator.reverseOrder())
                .mapToInt(i -> i)
                .toArray();

        System.out.println(Arrays.toString(ints));


    }
}
