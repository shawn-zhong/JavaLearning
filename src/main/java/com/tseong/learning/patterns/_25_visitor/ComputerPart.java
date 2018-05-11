package com.tseong.learning.patterns._25_visitor;

public interface ComputerPart {
    void accept(ComputerPartVisitor computerPartVisitor);
}
