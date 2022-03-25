package com.programmers.java.baseball;

import com.programmers.java.baseball.engine.Baseball;

public class App {
    public static void main(String[] args) {

        NumberGenerator generator = new HackFakerNumberGenerator();

        Console console = new Console();
        new Baseball(generator, console, console).run();

    }
}
