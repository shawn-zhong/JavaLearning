package com.tseong.learning.patterns._25_0_visitor;

public class StringElement implements Visitable {

    private String se;

    public StringElement(String se) {
        this.se = se;
    }

    public String getSe() {
        return this.se;
    }


    @Override
    public void accept(Visitor visitor) {
        visitor.visitString(this);
    }
}
