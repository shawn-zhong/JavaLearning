package com.tseong.learning.patterns._05_factory;

import com.tseong.learning.patterns.PayModel;
import com.tseong.learning.patterns.SettleModel;
import com.tseong.learning.patterns._02_template.AliPayProcessor;
import com.tseong.learning.patterns._02_template.ClassSettleProcessor;
import com.tseong.learning.patterns._02_template.PayTemplate;
import com.tseong.learning.patterns._02_template.WalletPayProcessor;

// "支付处理" 工厂类
public class PayAndSettleFactory {

    public static PayTemplate getPayProcessor(PayModel modle) throws Exception {
        switch (modle.getType()) {
            case 0 : return new AliPayProcessor(modle);
            case 1 : return new WalletPayProcessor(modle);
            default: throw new Exception("无法识别的支付类型");
        }
    }

    public static ClassSettleProcessor getSettleProcessor(SettleModel model) throws Exception {
        return new ClassSettleProcessor(model);
    }
}
