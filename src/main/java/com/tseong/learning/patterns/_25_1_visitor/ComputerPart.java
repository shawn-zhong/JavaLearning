package com.tseong.learning.patterns._25_1_visitor;

public interface ComputerPart {
    void accept(ComputerPartVisitor computerPartVisitor);
}
