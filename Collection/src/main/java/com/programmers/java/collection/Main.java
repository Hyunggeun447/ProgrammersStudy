package com.programmers.java.collection;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
/*
        List<Integer> list = new LinkedList<>();

        list.add(1);
        list.add(2);
        list.add(3);

        for (Integer integer : list) {
            System.out.println("integer = " + integer);
        }

        new MyCollection<>(Arrays.asList("A", "B", "C", "D", "E"))
                .forEach(a -> System.out.println("integer = " + a));*/

        MyCollection<Integer> c1 = new MyCollection<>(Arrays.asList(1, 2, 3, 4, 5));

        MyCollection<String> c2 = c1.map(data -> "1" + data);

        c2.forEach(s -> System.out.println("s = " + s));

        MyCollection<String> c3 = new MyCollection<>(Arrays.asList("ASD", "DADFV", "ZCXV", "GGGDDD", "VCVXWER"));

        MyCollection<Integer> c4 = c3.map(data -> data.length());

        c4.forEach(integer -> System.out.println("integer = " + integer));

/*
        new MyCollection<>(Arrays.asList("ASD", "DADFV", "ZCXV", "GGGDDD", "VCVXWER"))
                .map((data -> data.length()))
                .forEach(System.out::println);
        */
        new MyCollection<>(Arrays.asList("ASD", "DADFV", "ZCXV", "GGGDDD", "VCVXWER"))
                .map((data -> data.length()))
                .filter(integer -> integer % 2 == 0)
                .forEach(System.out::println);

        int size = new MyCollection<>(Arrays.asList("ASD", "DADFV", "ZCXV", "GGGDDD", "VCVXWER"))
                .map((data -> data.length()))
                .filter(integer -> integer % 2 == 0)
                .size();

        System.out.println("size = " + size);

    }
}
