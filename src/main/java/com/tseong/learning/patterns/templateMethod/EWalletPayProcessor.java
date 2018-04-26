package com.tseong.learning.patterns.templateMethod;

public class EWalletPayProcessor extends PayProcessor {

    @Override
    public boolean checkPayParameters() {
        return false;
    }

}
