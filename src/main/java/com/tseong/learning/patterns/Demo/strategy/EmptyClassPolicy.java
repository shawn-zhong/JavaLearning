package com.tseong.learning.patterns.Demo.strategy;

import com.tseong.learning.patterns.Demo.PayOrder;
import com.tseong.learning.patterns.Demo.PayPreOrder;
import com.tseong.learning.patterns.Demo.SettleModel;

import java.util.List;

// 适用于"空跑班"的结算策略
public class EmptyClassPolicy implements MoneyBackPolicy {
    @Override
    public List<PayPreOrder> chargeAndGenerateOrders(SettleModel settleModel) {
        // 对于没人参加的跑班，直接“已结清”跑班， 不产生结算信息
        // actions here

        return null;
    }
}
