package com.tseong.learning.patterns._25_0_visitor;

import java.util.Collection;

public interface Visitor {

    void visitFloat(FloatElement floatE);

    void visitString(StringElement stringE);

    void visitCollection(Collection collection);
}
