package com.tseong.learning.patterns.Demo.template;

import com.tseong.learning.patterns.Demo.PayOrder;
import com.tseong.learning.patterns.Demo.SettleModel;
import com.tseong.learning.patterns.Demo.SettleResultModel;
import com.tseong.learning.patterns.Demo.strategy.MoneyBackPolicy;

import java.util.LinkedList;
import java.util.List;

// "班级结帐"处理类
public class ClassSettleProcessor extends SettleTemplate {

    private List<MoneyBackPolicy> settlePolicies = new LinkedList<>();

    public ClassSettleProcessor(SettleModel model) {
        super(model);
    }

    public void addSettlePolicy(MoneyBackPolicy policy) {
        settlePolicies.add(policy);
    }

    @Override
    public void checkParameters() throws Exception {
        // 检查跑班结帐参数
    }

    @Override
    protected List<PayOrder> createOrders() throws Exception {
        // 根据跑班结算策略生成订单

        List<PayOrder> totalList = new LinkedList<>();

        for (MoneyBackPolicy policy : settlePolicies) {
            List<PayOrder> list = policy.chargeAndGenerateOrders(settleModel);
            totalList.addAll(list);
        }
        return totalList;
    }

    @Override
    protected void postPaymentActions(List<PayOrder> orders, SettleResultModel result) throws Exception {
        // 根据处理结果发送小秘书通知什么的
        // actions

        System.out.println("跑班结算成功" + settleModel.toString() + result.toString() );
    }
}
