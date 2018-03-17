package com.tseong.learning.thread.exchanger;

import java.util.concurrent.Exchanger;

public class ExchangerDemo {

    public static void main(String[] args) {
        final Exchanger<String> exchanger = new Exchanger<>();

        Thread thread1 = new Thread(new ExchangeRunnable("shawn", exchanger));
        thread1.start();

        Thread thread2 = new Thread(new ExchangeRunnable("Celestine", exchanger));
        thread2.start();

    }

    static class ExchangeRunnable implements Runnable {

        private String data;
        private Exchanger<String> exchanger;

        public ExchangeRunnable(String data, Exchanger<String> exchanger) {
            this.data = data;
            this.exchanger = exchanger;
        }

        @Override
        public void run() {

            try {
                System.out.println(Thread.currentThread().getName() + " is exchanging data " + data);
                Thread.sleep((long)(Math.random() * 1000));

                String data2 = exchanger.exchange(data);
                System.out.println(Thread.currentThread().getName() + " exchanged data " + data );

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


/*

Thread-1 is exchanging data Celestine
Thread-0 is exchanging data shawn
Thread-0 exchanged data shawn
Thread-1 exchanged data Celestine

 */
