package com.programmers.others;

import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Faker faker = new Faker();

        Integer[] integers = Stream.generate(() -> faker.number().randomDigitNotZero())
                .distinct()
                .limit(5)
                .toArray(Integer[]::new);
//                .forEach(System.out::println);

        System.out.println("integers = " + Arrays.toString(integers));

        User user = new User(1,"AAA");
        User user2 = new User(1,"23");

        System.out.println("user.toString() = " + user.toString());
        System.out.println(user.equals(user2));
        System.out.println("user2.getName() = " + user2.getName());
        user2.setName("AAA");
        System.out.println("user2.getName() = " + user2.getName());
    }
}
