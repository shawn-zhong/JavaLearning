package com.tseong.learning.patterns.Demo.template;

import com.tseong.learning.patterns.Demo.PayOrder;
import com.tseong.learning.patterns.Demo.SettleModel;
import com.tseong.learning.patterns.Demo.SettleResultModel;

import java.util.List;

// "班级结账"模版类
public abstract class SettleTemplate {

    protected SettleModel settleModel;
    protected List<PayOrder> orders;

    public SettleTemplate(SettleModel model) {
        this.settleModel = model;
    }

    public SettleResultModel settle() throws Exception {

        checkParameters();          // 检查支付参数

        checkPaymentEnvironment();      // 检查支付环境

        prePaymentActions();            // 支付前处理

        orders = createOrders();

        SettleResultModel result = doGateWayPayment(orders);     // 支付动作

        persitOrders(orders, result);                 // 实例化订单等

        postPaymentActions(orders, result);           // 支付后动作

        return result;
    }

    protected abstract void checkParameters() throws Exception ;

    protected void prePaymentActions() throws Exception {};

    protected void postPaymentActions(List<PayOrder> orders, SettleResultModel model) throws Exception {};

    protected SettleResultModel doGateWayPayment(List<PayOrder> orders) throws Exception {
        // common implementation here
        return null;
    }

    protected void checkPaymentEnvironment() throws Exception {
        // common implementation here
    }

    protected List<PayOrder> createOrders() throws Exception {
        // common implementation here
        return null;
    };

    protected void persitOrders(List<PayOrder> orders, SettleResultModel model) throws Exception {
        // common implementation here
    };
}
