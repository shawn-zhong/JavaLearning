package com.tseong.learning.advance._09_pattern.builder;

public class PersonTest {

    public static void main(String[] args) {
        Person person = new Person.Builder(1, "张三").age(18)
                .gender("male").desc("使用builder模式").build();

        System.out.println(person.toString());
    }
}
