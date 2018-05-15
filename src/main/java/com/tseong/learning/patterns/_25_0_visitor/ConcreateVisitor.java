package com.tseong.learning.patterns._25_0_visitor;

import java.util.Collection;
import java.util.Iterator;

public class ConcreateVisitor implements Visitor {

    @Override
    public void visitFloat(FloatElement floatE) {
        System.out.println(floatE.getFe());
    }

    @Override
    public void visitString(StringElement stringE) {
        System.out.println(stringE.getSe());
    }

    @Override
    public void visitCollection(Collection collection) {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object o = iterator.next();
            if (o instanceof Visitable) {
                ((Visitable)o).accept(this);
            }
        }
    }
}
