package com.tseong.learning.patterns._25_1_visitor;

public class VisitorPatternDemo {

    public static void main(String[] args) {
        ComputerPart computerPart = new Computer();
        computerPart.accept(new ComputerPartDisplayVisitor());
    }
}


/*

感觉要换个例子再感受一下

 */