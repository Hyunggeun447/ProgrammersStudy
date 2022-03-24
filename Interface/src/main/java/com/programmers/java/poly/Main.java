package com.programmers.java.poly;

public class Main {
    public static void main(String[] args) {
        /*//로그인을 하고싶을 때
        Main m = new Main();
        m.run(LoginType.Kakao); // 호스트 코드
*/

        UserService userService = new UserService(new NaverLogin());
        userService.login();

    }

    /*void run(LoginType loginType) {
        Login user = getLogin(loginType);

        user.login();
    }


    //팩토리 패턴
    private static Login getLogin(LoginType type) {
        if (type == LoginType.Kakao) {
            return new KakaoLogin();
        } else {
            return new NaverLogin();
        }
    }*/
}
