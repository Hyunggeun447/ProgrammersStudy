package com.programmers.java.lambda;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main2 {
    public static void main(String[] args) {
        Main2 main2 = new Main2();
        main2.loop(10, System.out::println);

        main2.filteredNumbers(30, i -> i % 2 == 0, System.out::println);

    }

    void loop(int n, MyConsumer<Integer> consumer) {
        for (int i = 0; i < n; i++) {
            consumer.consume(i);
        }
    }

    void filteredNumbers(int max, Predicate<Integer> p, Consumer<Integer> consumer) {
        for (int i = 0; i < max; i++) {
            if (p.test(i)) {
                consumer.accept(i);
            }
        }
    }


}
