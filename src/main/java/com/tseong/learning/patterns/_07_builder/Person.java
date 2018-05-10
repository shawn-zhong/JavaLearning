package com.tseong.learning.patterns._07_builder;

public class Person {

    // 必要参数
    private final int id;
    private final String name;


    // 可选参数
    private int age;
    private final String gender;
    private final String phone;
    private final String address;
    private final String desc;

    private Person(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.age = builder.age;
        this.gender = builder.gender;
        this.phone = builder.phone;
        this.address = builder.address;
        this.desc = builder.desc;
    }

    @Override
    public String toString() {
        return String.format("{age:%d, gender:%s, phone:%s, address:%s, desc:%s}", age, gender, phone, address, desc);
    }


    public static class Builder {
        // 必要参数
        private int id;
        private String name;


        // 可选参数
        private int age;
        private String gender;
        private String phone;
        private String address;
        private String desc;

        public Builder(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder age(int val) {
            this.age = val;
            return this;
        }

        public Builder gender(String val) {
            this.gender = val;
            return this;
        }

        public Builder phone(String val) {
            this.phone = val;
            return this;
        }

        public Builder address(String val) {
            this.address = val;
            return this;
        }

        public Builder desc(String val) {
            this.desc = val;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
