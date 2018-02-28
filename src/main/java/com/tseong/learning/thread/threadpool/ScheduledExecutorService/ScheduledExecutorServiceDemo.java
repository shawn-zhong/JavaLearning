package com.tseong.learning.thread.threadpool.ScheduledExecutorService;

import java.time.LocalTime;
import java.util.Objects;
import java.util.concurrent.*;

public class ScheduledExecutorServiceDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorServiceDemo instance = new ScheduledExecutorServiceDemo();
        instance.scheduleDemo();
        instance.scheduleAtFiexedRateDemo();
        instance.scheduleAtFixedDelay();
    }

    public void scheduleDemo() throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduleExecutorService = Executors.newScheduledThreadPool(5); // 预制5个线程

        // showcase runnable & callable
        ScheduledFuture scheduledFuture =
                scheduleExecutorService.schedule( ()->{ // new runnable
                            System.out.println("Reached condition and runnable method executed");
                        }
                        ,
                        5,
                        TimeUnit.SECONDS);

        // 因为是callable，将会阻塞等待结束并返回null
        System.out.println("scheduledExecutorService runnable returns : " + Objects.toString(scheduledFuture.get()));

        // showcase callable
        ScheduledFuture<String> scheduledFuture2 =
                scheduleExecutorService.schedule( () -> {
                            System.out.println("Reached condition and callable method executed");
                            return "demovalue";
                        },
                        3,
                        TimeUnit.SECONDS);

        // 因为是callable，将会阻塞等待结束并返回null
        System.out.println("scheduledExecutorService callable returns : " + Objects.toString(scheduledFuture2.get()));

        System.out.println("main thread over");

        scheduleExecutorService.shutdown();
    }

    /**
     * period 被解释为前一个执行的开始和下一个执行的开始之间的间隔时间
     * 如果给定任务的执行抛出了异常，该任务将不再执行。如果没有任何异常的话，这个任务将会持续循环执行到 ScheduledExecutorService 被关闭
     * 如果一个任务占用了比计划的时间间隔更长的时候，下一次执行将在当前执行结束执行才开始。计划任务在同一时间不会有多个线程同时执行。
     */
    public void scheduleAtFiexedRateDemo() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        scheduledExecutorService.scheduleAtFixedRate(() ->{
            System.out.println("FixedRate Timestamp " + LocalTime.now().toString());
            System.out.println("FixedRate start: init to execute every 10 secs");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            System.out.println("FixedRate end: init to execute every 10 secs");
        },
                3, 10, TimeUnit.SECONDS);

        //scheduledExecutorService.shutdown();
    }

    /**
     * period 则被解释为前一个执行的结束和下一个执行的结束之间的间隔。
     * 因此这个延迟是执行结束之间的间隔，而不是执行开始之间的间隔
     */
    public void scheduleAtFixedDelay() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        scheduledExecutorService.scheduleWithFixedDelay(() ->{
                    System.out.println("FixedDelay Timestamp " + LocalTime.now().toString());
                    System.out.println("FixedDelay start: init to execute every 10 secs");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("FixedDelay end: init to execute every 10 secs");
        },
                3, 10, TimeUnit.SECONDS);

        // scheduledExecutorService.shutdown();
    }
}
