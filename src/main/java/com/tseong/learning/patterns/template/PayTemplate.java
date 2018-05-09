package com.tseong.learning.patterns.template;

import com.tseong.learning.patterns.PayModel;
import com.tseong.learning.patterns.PayOrder;
import com.tseong.learning.patterns.PayResultModel;

import java.util.List;

// "支付"模版类
public abstract class PayTemplate {

    protected PayModel payModel;
    protected List<PayOrder> orders;

    public PayTemplate(PayModel payModel) {
        this.payModel = payModel;
    }

    public PayResultModel pay() throws Exception {

        checkParameters();          // 检查支付参数

        checkPaymentEnvironment();      // 检查支付环境

        prePaymentActions();            // 支付前处理

        orders = createOrders();

        PayResultModel result = doGateWayPayment(orders);     // 支付动作

        persitOrders(orders, result);                 // 实例化订单等

        postPaymentActions(orders, result);           // 支付后动作

        return result;
    }

    protected abstract void checkParameters() throws Exception ;

    protected void prePaymentActions() throws Exception {};

    protected void postPaymentActions(List<PayOrder> orders, PayResultModel model) throws Exception {};

    protected PayResultModel doGateWayPayment(List<PayOrder> orders) throws Exception {
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

    protected void persitOrders(List<PayOrder> orders, PayResultModel model) throws Exception {
        // common implementation here
    };
}

