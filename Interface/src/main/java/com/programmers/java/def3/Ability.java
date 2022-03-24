package com.programmers.java.def3;

public interface Ability {
    static void sayHello(){
        System.out.println("hello~~");
    }

}
interface Flyable {
    default void fly(){
        System.out.println("Fly");

    }
}

interface Swinmable {
    default void swim(){
        System.out.println("Swim");

    }
}

interface Walkable {
    default void walk(){
        System.out.println("Walk");

    }
}
