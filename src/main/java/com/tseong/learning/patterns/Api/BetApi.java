package com.tseong.learning.patterns.Api;

import com.tseong.learning.patterns.PayModel;
import com.tseong.learning.patterns.PayResultModel;
import com.tseong.learning.patterns.SettleModel;
import com.tseong.learning.patterns.SettleResultModel;
import com.tseong.learning.patterns.factory.MoneyPolicyFactory;
import com.tseong.learning.patterns.factory.PayAndSettleFactory;
import com.tseong.learning.patterns.state.base.BetClass;
import com.tseong.learning.patterns.strategy.MoneyBackPolicy;
import com.tseong.learning.patterns.template.ClassSettleProcessor;
import com.tseong.learning.patterns.template.PayTemplate;

import java.util.Arrays;
import java.util.List;

public class BetApi {

    public static void main() throws Exception {
        BetApi api = new BetApi();
        api.processPayment(null);
        api.settleClass(null);
        api.generateDiploma();
    }

    // 处理用户支付
    public PayResultModel processPayment(PayModel model) throws Exception {
        PayTemplate payTemplate = PayAndSettleFactory.getPayProcessor(model);
        return payTemplate.pay();
    }

    // 处理跑班结帐
    public SettleResultModel settleClass(SettleModel model) throws Exception {
        ClassSettleProcessor settleTemplate = PayAndSettleFactory.getSettleProcessor(model);
        MoneyBackPolicy policy = MoneyPolicyFactory.getMoneyBackPolicy(model);
        settleTemplate.addSettlePolicy(policy);
        return settleTemplate.settle();
    }

    // 为相关跑班生成毕业证书
    public void generateDiploma() throws Exception {

        // 从DB里或某个容器里拿到相关的已结帐未生成毕业证书的所有跑班id：
        List<Integer> settledClasses = Arrays.asList(11, 22, 3);

        for (Integer classId : settledClasses) {
            BetClass theBetClass = BetClass.initBetClassFromDB(classId);    // 初始化跑班对象
            theBetClass.generateClassDiploma();                             // 生成毕业证书
        }
    }
}
