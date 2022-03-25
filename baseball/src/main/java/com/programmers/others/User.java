package com.programmers.others;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class User {

    private int age;
    private String name;
}
