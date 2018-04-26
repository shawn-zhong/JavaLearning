package com.tseong.learning.patterns.states;

import com.tseong.learning.patterns.strategery.ChargePolicy;
import com.tseong.learning.patterns.templateMethod.PayProcessor;

public abstract class PayState {

    ChargePolicy policy;


    public abstract void Handle(PayProcessor processor);
}
