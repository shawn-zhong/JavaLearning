package com.tseong.learning.basic.thread.threadpool.ForkJoinPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

// RecursiveTask 是一种会返回结果的任务
// RecursiveAction 是一种没有任何返回值的任务。它只是做一些工作，比如写数据到磁盘，然后就退出了。
// 一个 RecursiveAction 可以把自己的工作分割成更小的几块，这样它们可以由独立的线程或者 CPU 执行。
public class MakeMoneyTask extends RecursiveTask<Integer> {

    private static final int MIN_GOAL_MONEY = 100000;
    private int goalMoney;
    private String name;
    private static final AtomicLong employeeNo = new AtomicLong();

    public MakeMoneyTask(int goalMoney) {
        this.goalMoney = goalMoney;
        this.name = "员工" + employeeNo.getAndIncrement() + "号";
    }

    private Integer makeMoney() {
        int sum = 0;
        int day = 1;
        try {
            while (true) {
                Thread.sleep(ThreadLocalRandom.current().nextInt(500));
                int money = ThreadLocalRandom.current().nextInt(MIN_GOAL_MONEY / 3);
                System.out.println(name + ": on day " + (day++) + "earned " + money);
                sum += money;
                if (sum >= goalMoney) {
                    System.out.println(name + " in total earned " + sum + ", job done");
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sum;
    }

    @Override
    protected Integer compute() {
        if (this.goalMoney < MIN_GOAL_MONEY) {
            System.out.println(name + ": boss asks me to earn " + goalMoney);
            return makeMoney();
        } else {
            int subThreadCount = ThreadLocalRandom.current().nextInt(10) + 2;
            System.out.println(name + ": boss asks me to earn" + goalMoney + ", too much, "
                + "I will break it down onto " + subThreadCount +" person " + " each of them has" +
                    "task only " + Math.ceil(goalMoney *1.0 / subThreadCount) + " RMB" );
            List<MakeMoneyTask> tasks = new ArrayList<>();
            for (int i=0; i < subThreadCount; i++) {
                tasks.add(new MakeMoneyTask(goalMoney / subThreadCount));
            }
            Collection<MakeMoneyTask> makeMoneyTasks = invokeAll(tasks);
            int sum = 0;
            for (MakeMoneyTask moneyTask : makeMoneyTasks) {
                try {
                    sum += moneyTask.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("at total me and my stuff earned " + sum);
            return sum;
        }
    }
}
