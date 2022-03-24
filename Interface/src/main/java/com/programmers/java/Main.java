package com.programmers.java;

interface YourRunnable {
    void yourRun();
}

public class Main implements Runnable, YourRunnable{
    public static void main(String[] args) {
        System.out.println("hello");

        Runnable m = new Main();
        m.run();
        YourRunnable y = new Main();
        y.yourRun();

    }

    @Override
    public void run() {
        System.out.println("hello run");

    }

    @Override
    public void yourRun() {
        System.out.println("your run");

    }
}
