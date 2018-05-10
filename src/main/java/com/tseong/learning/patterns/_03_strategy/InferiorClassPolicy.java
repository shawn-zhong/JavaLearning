package com.tseong.learning.patterns._03_strategy;

import com.tseong.learning.patterns.PayPreOrder;
import com.tseong.learning.patterns.SettleModel;


import java.util.ArrayList;
import java.util.List;

// 适用于"没有一个人毕业的跑班"的结算策略
public class InferiorClassPolicy implements MoneyBackPolicy {
    @Override
    public List<PayPreOrder> chargeAndGenerateOrders(SettleModel settleModel) {

        List<PayPreOrder> orders = new ArrayList<>(5);

        for (Integer userId : settleModel.getUsers()) {
            int deposit = 200;                              // 1 计算返还：对未完成打卡部分直接收取50%费用，示例结果200
            orders.add(new PayPreOrder(deposit));              // 2 生成订单

            if (settleModel.getSponsorAmount(userId) > 0) {                         // 3 返回赞助金额
                orders.add(new PayPreOrder(settleModel.getSponsorAmount(userId)));     // 4 生成订单
            }
        }

        return orders;      // 5. 应用策略后返回的结算订单集合
    }
}
