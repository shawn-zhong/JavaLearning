package com.tseong.learning.container.concurrent.blockingQueue.DelayQueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedElement implements Delayed {

    private final long delayTime;   // 延迟时间
    private final long expired;     // 到期时间
    private String data;    // 数据

    public DelayedElement(long delay, String data) {
        delayTime = delay;
        expired = System.currentTimeMillis() + delay;
        this.data = data;
    }
    /**
     * 剩余时间 = 到期时间 - 当前时间
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expired - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 优先队列里面优先级规则
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DelayedElement{");
        sb.append("delay=").append(delayTime);
        sb.append(", expire=").append(expired);
        sb.append(", data=").append(data);
        sb.append("}");
        return sb.toString();
    }
}

// https://www.jianshu.com/p/2659eb72134b