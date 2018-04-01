package com.tseong.learning.basic.thread.semaphore;

public class Bootstrap {

    public static void main(String[] args) {
        ResourceManager resourceManager = new ResourceManager();

        for (int i=0; i<100; i++) {
            Thread thread = new Thread(new ResourceUser(resourceManager, i));
            thread.start();
        }

    }
}
