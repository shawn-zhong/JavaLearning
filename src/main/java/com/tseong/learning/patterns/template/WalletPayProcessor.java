package com.tseong.learning.patterns.template;

import com.tseong.learning.patterns.PayModel;
import com.tseong.learning.patterns.PayOrder;
import com.tseong.learning.patterns.PayResultModel;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

// "钱包支付"处理类
public class WalletPayProcessor extends PayTemplate {

    public WalletPayProcessor(PayModel payModel) {
        super(payModel);
    }

    @Override
    protected void checkParameters() throws Exception {
        // 检查支付参数是否符合钱包支付条件，比如钱包支付暂时只对内部开放：
        if (payModel.getIsTestUser()) {
            throw new Exception("悦跑圈钱包暂时仅对内测用户开放");
        }
    }

    @Override
    protected void checkPaymentEnvironment() throws Exception {
        // 比如检查当前的环境是否符合钱包支付的条件
    }

    @Override
    protected PayResultModel doGateWayPayment(List<PayOrder> orders) throws Exception {
        // 比如调用特定网关实现钱包支付
        throw new NotImplementedException();
    }

    @Override
    protected void postPaymentActions(List<PayOrder> orders, PayResultModel result) throws Exception {
        // 根据处理结果发送小秘书通知什么的
        // actions

        System.out.println("用户使用钱包支付成功:" + result.toString());
    }
}
