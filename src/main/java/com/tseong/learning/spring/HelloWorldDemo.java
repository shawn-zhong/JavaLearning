package com.tseong.learning.spring;

public class HelloWorldDemo implements HelloWorldInterface {

    private String word = "Hello from code";
    private String language = "Chinese";
    
    public void setWord(String word) {
        this.word = word;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String sayHello() {
        return word + " " + language;
    }

}
