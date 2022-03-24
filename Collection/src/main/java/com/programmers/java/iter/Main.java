package com.programmers.java.iter;

import com.programmers.java.collection.MyCollection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        List<String> list = Arrays.asList("A", "DA", "ASD", "SDFG", "ERGFFC");

//        Iterator<String> iterator = list.iterator();
/*
        System.out.println("iterator = " + iterator.next());
        System.out.println("iterator = " + iterator.next());
        System.out.println("iterator = " + iterator.next());
        System.out.println("iterator = " + iterator.next());
        System.out.println("iterator = " + iterator.next());
        if (iterator.hasNext()) System.out.println("iterator = " + iterator);*/
/*

        while (iterator.hasNext()) {
            System.out.println("iterator = " + iterator.next());
        }
*/

        MyIterator<String> iterator1 =
                new MyCollection<String>(Arrays.asList("A", "DS", "FDD", "WEEW", "CXVSE"))
                        .iterator();

        while (iterator1.hasNext()) {
            String next = iterator1.next();
            int len = next.length();
            if (len % 2 == 0) {
                continue;
            }
            System.out.println("iterator1 = " + next);
        }


    }
}
