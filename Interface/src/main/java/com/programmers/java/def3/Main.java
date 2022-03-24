package com.programmers.java.def3;


class Duck implements Swinmable, Walkable {}

class Swan implements Flyable, Walkable, Swinmable {
}

public class Main {
    public static void main(String[] args) {

        Duck duck = new Duck();
        Swan swan = new Swan();

        duck.swim();
        duck.walk();

        swan.fly();
        swan.walk();
        swan.swim();

        Ability.sayHello();



    }
}
