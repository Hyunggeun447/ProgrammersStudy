package com.programmers.java.optional;

import com.programmers.java.collection.User;

import java.util.Optional;

public class Main2 {
    public static void main(String[] args) {

        Optional<User> optionalUser = Optional.empty(); //null

        optionalUser = Optional.of(new User(1, "aAA"));

//        User user = optionalUser.get();
//        System.out.println("user = " + user);

        boolean empty = optionalUser.isEmpty();
        System.out.println("empty = " + empty);

        boolean present = optionalUser.isPresent();
        System.out.println("present = " + present);

        optionalUser.ifPresent(user -> {
//            do 1
        });

        optionalUser.ifPresentOrElse(user -> {
//            do 1
        }, ()->{
//            do 2
        });

    }

}
