package com.programmers.java.collection;

public class User {
    public static final User EMPTY = new User(0, "");

    private int age;

    private String name;
    
    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
    /*
    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }*/

    public boolean isOver(int age) {
        return this.age > age;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
