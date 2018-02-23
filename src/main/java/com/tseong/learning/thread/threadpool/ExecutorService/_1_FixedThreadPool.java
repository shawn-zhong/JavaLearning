package com.tseong.learning.thread.threadpool.ExecutorService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class _1_FixedThreadPool {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        _1_FixedThreadPool instance = new _1_FixedThreadPool();

        instance.executeDemo();
        instance.callableAndRunnable();
        instance.invokeAnyDemo();
    }

    void executeDemo() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // execute 方法没有返回值
        for (int i=0; i<10; i++) {
            executorService.execute(() -> {
                System.out.println("thread name: " + Thread.currentThread().getName());
            });

            Thread.sleep(100);
        }

        executorService.shutdown();
    }

    void callableAndRunnable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // submit(Callable)方法返回一个Future对象，可以使用这个Future检查是否执行完毕以及返回值
        Future future = executorService.submit(() ->{
            System.out.println("submit(Callable)");
            Thread.sleep(3000);
            return "a";
        });

        Object obj = future.get(); // 会一直阻塞到执行完毕并获得返回值"a"
        System.out.println("submit result : " + obj.toString());

        // submit(Callable)方法返回一个Future对象，可以使用这个Future检查是否执行完毕以及返回值
        Future future2 = executorService.submit(() ->{
            System.out.println("submit(Runnable)");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Object obj2 = future2.get(); // 会一直阻塞到执行完毕并获得返回值null
        // System.out.println("submit result : " + obj2.toString()); // nullpointerexception
        executorService.shutdown();
    }

    void invokeAnyDemo() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor(); // 数量为1的线程池，多包装了一个代理类，GC时会shutdown
        Set<Callable<String>> callables = new HashSet<Callable<String>>();

        for (int i=0; i<5; i++) {
            final int tmp = i;
            callables.add(()->{
               return "Task : " + tmp;
            });
        }

        // invokeAny() 方法要求一系列的 Callable 或者其子接口的实例对象。调用这个方法并不会返回一个 Future，
        // 但它返回其中一个 Callable 对象的结果。无法保证返回的是哪个 Callable 的结果 -
        // 只能表明其中一个已执行结束。
        String result = executorService.invokeAny(callables);
        System.out.println("Invoke Any result : " + result);

        // invokeAll() 返回一系列的 Future 对象，通过它们你可以获取每个 Callable 的执行结果。
        // 记住，一个任务可能会由于一个异常而结束，因此它可能没有 "成功"。无法通过一个 Future 对象来告知我们是两种结束中的哪一种。
        List<Future<String>> futures = executorService.invokeAll(callables);

        for (Future<String> future : futures) {
            System.out.println("Invoke All return: " + future.get());
        }

        executorService.shutdown();
    }
}


/*

newCachedThreadPool     必要时创建新线程，空闲线程会被保留60秒
newFixedThreadPool      该池包含固定数量的线程，空闲一定会被保留，如果任
                        务数量多线程数量，多出的任务被放在队列中缓存，直
                        到有空闲线程可以执行为止
newSingleThreadExecutor  只有一个线程的newFixedThreadPool
newScheduledThreadPool   java.util.Timer内部是由一个线程来维护并执行Timer
                         内部的多个TimerTask的，而该执行器类可以看做是由
                         多个线程来完成这些TimerTask的，这样并发性更好。

ThreadPoolExecutor提交任务主要是通过以下3个方法来完成的，如下：
Future<?> submit(Runnable task) 该返回的Future<?>只能用于isDone()调用来判断任务是否执行完毕，而无法通过get方法获取任务执行后的返回值，因为他将始终为null。
Future<T> submit(Runnable task,T result) 该任务的返回值为result对象。
Future<T> submit(Callable task); 返回的Future对象可以通过get方法获取Callable执行后的返回值对象。

以下两个方法用于关闭执行器
    shutdown() 该方法被执行后，执行器将不会再接收新的任务，在执行完已经提交的全部任务之后，执行器将退出所有工作者线程。
    shutdownNow();  和shutdown最主要的区别是该函数并不执行尚未执行的任务了(缓存在队列中的)，而是试图第一个时间关闭整个执行器。
 */
