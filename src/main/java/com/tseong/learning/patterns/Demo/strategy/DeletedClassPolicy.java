package com.tseong.learning.patterns.Demo.strategy;


import com.tseong.learning.patterns.Demo.PayOrder;
import com.tseong.learning.patterns.Demo.PayPreOrder;
import com.tseong.learning.patterns.Demo.SettleModel;

import java.util.ArrayList;
import java.util.List;

// 适用于"删除的跑班"的结算策略
public class DeletedClassPolicy implements MoneyBackPolicy {

    @Override
    public List<PayPreOrder> chargeAndGenerateOrders(SettleModel settleModel) {
        List<PayPreOrder> orders = new ArrayList<>(5);

        for (Integer userId : settleModel.getUsers()) {
            int deposit = settleModel.getDepositEachTime();     // 1 返回每个用户的约定金额
            orders.add(new PayPreOrder(deposit));                  // 2 生成订单

            if (settleModel.getSponsorAmount(userId) > 0) {     // 3 如果该用户是赞助用户，返还赞助金额
                orders.add(new PayPreOrder(settleModel.getSponsorAmount(userId)));    // 4 生成订单
            }
        }

        return orders;      // 5. 应用策略后返回的结算订单集合
    }
}
