package com.tseong.learning.patterns._25_0_visitor;

public class FloatElement implements Visitable {

    private Float fe;

    public FloatElement(Float fe) {
        this.fe = fe;
    }

    public Float getFe() {
        return this.fe;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitFloat(this);
    }

}
