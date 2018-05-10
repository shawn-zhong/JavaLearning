package com.tseong.learning.patterns._02_template;

import com.tseong.learning.patterns.PayPreOrder;
import com.tseong.learning.patterns.SettleModel;
import com.tseong.learning.patterns.SettleResultModel;

import java.util.List;

// "班级结账"模版类
public abstract class SettleTemplate {

    protected SettleModel settleModel;
    protected List<PayPreOrder> orders;

    public SettleTemplate(SettleModel model) {
        this.settleModel = model;
    }

    public SettleResultModel settle() throws Exception {

        checkParameters();          // 检查支付参数

        checkPaymentEnvironment();      // 检查支付环境

        preMQActions();            // 支付前处理

        orders = createMQOrders();

        SettleResultModel result = sendPendingPayOrdersToMQ(orders);     // 支付动作

        //persitOrders(orders, result);                 // 实例化订单等

        postMQActions(orders, result);           // 支付后动作

        return result;
    }

    protected abstract void checkParameters() throws Exception ;

    protected void preMQActions() throws Exception {};

    protected void postMQActions(List<PayPreOrder> orders, SettleResultModel model) throws Exception {};

    protected SettleResultModel sendPendingPayOrdersToMQ(List<PayPreOrder> orders) throws Exception {
        // common implementation here
        return null;
    }

    protected void checkPaymentEnvironment() throws Exception {
        // common implementation here
    }

    protected List<PayPreOrder> createMQOrders() throws Exception {
        // common implementation here
        return null;
    };

    /*
    protected void persitOrders(List<PayPreOrder> orders, SettleResultModel model) throws Exception {
        // common implementation here
    };*/
}
