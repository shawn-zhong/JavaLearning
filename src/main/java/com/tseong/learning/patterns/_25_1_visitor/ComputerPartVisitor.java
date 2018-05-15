package com.tseong.learning.patterns._25_1_visitor;


public interface ComputerPartVisitor {

    void visit(Computer computer);
    void visit(Mouse mouse);
    void visit(Keyboard keyboard);
    void visit(Monitor monitor);

}
