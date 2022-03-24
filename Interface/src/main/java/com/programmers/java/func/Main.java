package com.programmers.java.func;

class Greeting implements MySupply {

    @Override
    public String supply() {
        return "Hello Supply";
    }

}
class SayHello implements MyRunnable {

    @Override
    public void run() {
        System.out.println("Say Hello");
    }
}

public class Main {
    public static void main(String[] args) {
        new SayHello().run();
        System.out.println("supply = " + new Greeting().supply());

        String supply = new MySupply() {
            @Override
            public String supply() {
                return "Hello Supply 익명클래스";
            }
        }.supply();
        System.out.println("supply = " + supply);

        MyRunnable r = new MyRunnable() {
            @Override
            public void run() {
                MySupply s = new MySupply() {
                    @Override
                    public String supply() {
                        return "hello supply 익명 익명 클래스";
                    }
                };
                System.out.println("s = " + s.supply());

            }
        };
        r.run();


    }
}
