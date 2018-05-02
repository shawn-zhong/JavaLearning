package com.tseong.learning.patterns.Demo.Api;

import com.tseong.learning.patterns.Demo.PayModel;
import com.tseong.learning.patterns.Demo.PayResultModel;
import com.tseong.learning.patterns.Demo.SettleModel;
import com.tseong.learning.patterns.Demo.SettleResultModel;
import com.tseong.learning.patterns.Demo.factory.MoneyPolicyFactory;
import com.tseong.learning.patterns.Demo.factory.PayAndSettleFactory;
import com.tseong.learning.patterns.Demo.strategy.MoneyBackPolicy;
import com.tseong.learning.patterns.Demo.template.PayTemplate;
import com.tseong.learning.patterns.Demo.template.SettleTemplate;

public class BetApi {

    public static void main() throws Exception {
        BetApi api = new BetApi();
        api.processPayment(null);
        api.settleClass(null);

    }

    // 处理用户支付
    public PayResultModel processPayment(PayModel model) throws Exception {
        PayTemplate payTemplate = PayAndSettleFactory.getPayProcessor(model);
        return payTemplate.pay();
    }

    // 处理跑班结帐
    public SettleResultModel settleClass(SettleModel model) throws Exception {
        SettleTemplate settleTemplate = PayAndSettleFactory.getSettleProcessor(model);
        MoneyBackPolicy policy = MoneyPolicyFactory.getMoneyBackPolicy(model);
        return settleTemplate.settle();
    }
}
