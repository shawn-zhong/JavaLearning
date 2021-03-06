package com.tseong.learning.patterns._02_template;

import com.tseong.learning.patterns.PayPreOrder;
import com.tseong.learning.patterns.SettleModel;
import com.tseong.learning.patterns.SettleResultModel;
import com.tseong.learning.patterns._03_strategy.MoneyBackPolicy;

import java.util.LinkedList;
import java.util.List;

// "班级结帐"处理类
public class ClassSettleProcessor1 extends SettleTemplate {

    private List<MoneyBackPolicy> settlePolicies = new LinkedList<>();

    public ClassSettleProcessor1(SettleModel model) {
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
    protected List<PayPreOrder> createMQOrders() throws Exception {
        // 根据跑班结算策略生成订单

        List<PayPreOrder> totalList = new LinkedList<>();

        for (MoneyBackPolicy policy : settlePolicies) {
            List<PayPreOrder> list = policy.chargeAndGenerateOrders(settleModel);
            totalList.addAll(list);
        }
        return totalList;
    }

    @Override
    protected void postMQActions(List<PayPreOrder> orders, SettleResultModel result) throws Exception {
        // 根据处理结果发送小秘书通知什么的
        // actions

        System.out.println("已发送结算订单到相关MQ处理" + settleModel.toString() + result.toString() );
    }
}
