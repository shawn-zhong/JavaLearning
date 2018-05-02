package com.tseong.learning.patterns.Demo.factory;

import com.tseong.learning.patterns.Demo.PayModel;
import com.tseong.learning.patterns.Demo.SettleModel;
import com.tseong.learning.patterns.Demo.template.ClassSettleProcessor;
import com.tseong.learning.patterns.Demo.template.AliPayProcessor;
import com.tseong.learning.patterns.Demo.template.PayTemplate;
import com.tseong.learning.patterns.Demo.template.SettleTemplate;
import com.tseong.learning.patterns.Demo.template.WalletPayProcessor;

// "支付处理" 工厂类
public class PayAndSettleFactory {

    public static PayTemplate getPayProcessor(PayModel modle) throws Exception {
        switch (modle.getType()) {
            case 0 : return new AliPayProcessor(modle);
            case 1 : return new WalletPayProcessor(modle);
            default: throw new Exception("无法识别的支付类型");
        }
    }

    public static SettleTemplate getSettleProcessor(SettleModel model) throws Exception {
        return new ClassSettleProcessor(model);
    }
}
