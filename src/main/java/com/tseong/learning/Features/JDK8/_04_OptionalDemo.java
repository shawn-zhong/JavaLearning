package com.tseong.learning.Features.JDK8;

//http://www.jb51.net/article/48304.htm

import java.util.Optional;

public class _04_OptionalDemo {

    public static void main(String[] args) {

        Optional<String> optional = Optional.ofNullable(null);

        boolean isPresent = optional.isPresent();   // true
       // String valueget = optional.get();
        String valueElse = optional.orElse("CHN");

       // System.out.println("Optional: boolean, valueget, valueElse : " + isPresent + " " + valueget + " " + valueElse);

        optional.ifPresent(s->System.out.println(s.charAt(0)));

        User user = new User();
        user.setName("shawn");
        user.setSurname("zhong");
        System.out.println(getChampionName(user));

    }

    public static String getChampionName(User user) {
        return Optional.ofNullable(user)
                .map(u->u.getName())
                .orElseThrow(() ->new IllegalArgumentException("The value input is invalid"));
    }

    static class User {
        private String name;
        private String surname;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

    }
}
