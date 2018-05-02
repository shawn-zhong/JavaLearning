package com.tseong.learning.patterns.states;

import com.tseong.learning.patterns.strategery.ChargePolicy;
import com.tseong.learning.patterns.templateMethod.PayProcessor;

public class PayPendingState extends PayState {
    public PayPendingState(ChargePolicy policy) {
       // super(policy);
    }

    @Override
    public void Handle(PayProcessor processor) {
        // processing ..
       // processor.setState(new PaySuccessState(policy));
    }
}
