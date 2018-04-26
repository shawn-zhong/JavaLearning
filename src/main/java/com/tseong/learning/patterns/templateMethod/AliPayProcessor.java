package com.tseong.learning.patterns.templateMethod;

public class AliPayProcessor extends PayProcessor {

    @Override
    public boolean checkPayParameters() {
        return false;
    }


}
