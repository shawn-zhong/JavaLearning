package com.tseong.learning.patterns.Demo.factory;

import com.tseong.learning.patterns.Demo.SettleModel;
import com.tseong.learning.patterns.Demo.strategy.DeletedClassPolicy;
import com.tseong.learning.patterns.Demo.strategy.EmptyClassPolicy;
import com.tseong.learning.patterns.Demo.strategy.MoneyBackPolicy;
import com.tseong.learning.patterns.Demo.strategy.NormalClassPolicy;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

// "结算策略"工厂类
public class MoneyPolicyFactory {

    public static MoneyBackPolicy getMoneyBackPolicy(SettleModel model) {
        switch (judegeModel(model)) {
            case 0 : return new DeletedClassPolicy();
            case 1 : return new EmptyClassPolicy();
            case 2 : return new NormalClassPolicy();
            default: assert(false); return null;
        }
    }

    private static int judegeModel(SettleModel model) {
        throw new NotImplementedException();
    }
}
