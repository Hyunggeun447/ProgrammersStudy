package com.programmers.java.collection;

import java.util.Arrays;

public class Main2 {
    public static void main(String[] args) {
        MyCollection<User> userMyCollection = new MyCollection<>(
                Arrays.asList(
                        new User(10, "name1"),
                        new User(11, "name2"),
                        new User(12, "name3"),
                        new User(13, "name4"),
                        new User(14, "name5"),
                        new User(15, "name6"),
                        new User(16, "name7"),
                        new User(17, "name8"),
                        new User(18, "name9"),
                        new User(19, "name10")
                )
        );
/*
        userMyCollection.filter(u -> u.getAge() >= 15)
                .map(data -> data.getName())
                .forEach(System.out::println);*/
        userMyCollection.filter(u -> u.isOver(15))
                .forEach(System.out::println);

    }



}
