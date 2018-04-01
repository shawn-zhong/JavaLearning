package com.tseong.learning.basic.thread.semaphore;



public class ResourceUser implements Runnable {
    private ResourceManager resourceManager;
    private int userId;

    public ResourceUser (ResourceManager resourceManager, int userId) {
        this.resourceManager = resourceManager;
        this.userId = userId;
    }

    public void run() {
        System.out.println("userId : " + userId + " is to use toilet");
        resourceManager.userResource(userId);
        System.out.println("userId : " + userId + " has used toilet");
    }
}
