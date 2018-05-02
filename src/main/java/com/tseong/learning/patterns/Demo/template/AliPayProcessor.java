package com.tseong.learning.patterns.Demo.template;

import com.tseong.learning.patterns.Demo.PayModel;
import com.tseong.learning.patterns.Demo.PayOrder;
import com.tseong.learning.patterns.Demo.PayResultModel;

import java.util.List;

// "阿里支付"处理类
public class AliPayProcessor extends PayTemplate {

    public AliPayProcessor(PayModel payModel) {
        super(payModel);
    }

    @Override
    protected void checkParameters() throws Exception {
        // 检查参数是否符合支付宝支付要求, 比如
        if (payModel.getAmount() > 10000)
            throw new Exception("系统不支持1万以上的支付宝支付");
    }

    @Override
    protected void prePaymentActions() throws Exception {
        // 比如可以支付前写写日志什么的, 或者加入调用网关需要的支付宝特定参数
    }

    @Override
    protected void postPaymentActions(List<PayOrder> orders, PayResultModel model) throws Exception {
        // 根据处理结果发送小秘书通知什么的
        // actions

        System.out.println("用户使用支付宝支付成功:" + model.toString());
    }

    // 具体支付流程，调用网关，插入订单 可以用模版提供的方法，就不需要再override
}
