package com.tseong.learning.bean.lifecycle;

import org.springframework.beans.factory.BeanNameAware;

import javax.annotation.PostConstruct;

/**
 * @author: Shawn ZHONG @date: 2020-05-29 17:34
 */
public class LifeCycleBean implements BeanNameAware {

    public LifeCycleBean(){
        System.out.println("\n\tConstructoring ...");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("\n\tBean name aware:" +name);
    }

    @PostConstruct
    public void postContructor() {
        System.out.println("\n\tPostContrutoring...");
    }


    public void init() {
        System.out.println("\n\tinitMethod entering..");
    }

    public void play() {
        System.out.println("\n\tplaying lifecycle");
    }

    public void destroy() {
        System.out.println("\n\tdestroyMethod entering..");
    }


    // bean的生命周期打印如下：
    /**
     *
     * Contructoring..   (构造函数)
     * Bean name aware.. （AWARE接口）
     * postProcessBeforeInitialization..
     * PostContrutoring
     * initMethod entering..
     * postProcessAfterInitialization..
     * playing lifecycle..
     * destroyMethod entering..
     */

}
