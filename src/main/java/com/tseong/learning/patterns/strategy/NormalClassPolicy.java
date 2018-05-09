package com.tseong.learning.patterns.strategy;

import com.tseong.learning.patterns.PayPreOrder;
import com.tseong.learning.patterns.SettleModel;

import java.util.ArrayList;
import java.util.List;

// 适用于"正常跑班"的结算策略
public class NormalClassPolicy implements MoneyBackPolicy {
    @Override
    public List<PayPreOrder> chargeAndGenerateOrders(SettleModel settleModel) {
        List<PayPreOrder> orders = new ArrayList<>(5);

        for (Integer userId : settleModel.getUsers()) {
            int deposit = 150;                      // 1 计算完成返还：打卡部分的约定金＋未完成打卡的约定金*(1-2%) 实现略
            orders.add(new PayPreOrder(deposit));      // 2 生成订单

            int bonus = 210;                        // 3 如果该人全部完成全部打卡，计算所得奖金 实现略
            orders.add(new PayPreOrder(bonus));        // 4 生成订单
        }

        return orders;      // 3. 应用策略后返回的结算订单集合
    }
}
