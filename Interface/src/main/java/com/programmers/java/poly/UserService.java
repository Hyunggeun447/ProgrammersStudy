package com.programmers.java.poly;

public class UserService implements Login {


    //login에 의존한다
    private Login login;

    //의존성을 외부에 맡긴다면? -> 의존성을 낮춘다
    //구상체와 결합하면 결합성 강하게 결합된다. -> 추상체와 결합하면 결합도가 낮아진다.
    //의존성을 외부로부터 전달받았다 -> 의존성을 주입받았다.
    //의존성 주입, Dependency Injection, DI
    //Dependency Inversion 의존성 역전 (Solid의 D)
    public UserService(Login login) {
        this.login = login;
    }

    @Override
    public void login() {
        login.login();
    }
}
