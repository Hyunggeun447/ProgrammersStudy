package com.programmers.java.optional;

import com.programmers.java.collection.User;

public class Main {
    public static void main(String[] args) {
        User aaa = new User(22, "AAA");

        User user = User.EMPTY;

        User user2 = getUser();

        if (user2 == User.EMPTY) {
            System.out.println("user2 = " + user2);
        }
    }

    private static User getUser() {
        return User.EMPTY;
    }
}
