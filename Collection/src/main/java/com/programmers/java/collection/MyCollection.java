package com.programmers.java.collection;

import com.programmers.java.iter.MyIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class MyCollection<T> {

    private List<T> list;

    public MyCollection(List<T> list) {
        this.list = list;
    }

    public <U> MyCollection<U> map(Function<T, U> function) {
        List<U> newList = new ArrayList<>();
        forEach(data -> newList.add(function.apply(data)));
        return new MyCollection<>(newList);
    }

    public void forEach(Consumer<T> consumer) {
        for (int i = 0; i < list.size(); i++) {

            T data = list.get(i);
            consumer.accept(data);

        }
    }

    public int size() {
        return list.size();
    }


    public MyCollection<T> filter(Predicate<T> predicate) {
        List<T> newList = new ArrayList<>();
        forEach(data -> {
            if(predicate.test(data)) newList.add(data);
                });

        return new MyCollection<>(newList);
    }

    public MyIterator<T> iterator() {
        return new MyIterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < list.size();
            }

            @Override
            public T next() {
                return list.get(index++);
            }

        };
    }


}
