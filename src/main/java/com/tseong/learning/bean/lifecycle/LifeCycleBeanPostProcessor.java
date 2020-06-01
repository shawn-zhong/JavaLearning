package com.tseong.learning.bean.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: Shawn ZHONG @date: 2020-05-29 17:52
 */
@Component
public class LifeCycleBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("shawnscyclebean") || bean instanceof LifeCycleBean) {
            System.out.println("\n\t postProcessBeforeInitialization");
        }

        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("shawnscyclebean") || bean instanceof LifeCycleBean) {
            System.out.println("\n\t postProcessAfterInitialization");
        }

        return bean;
    }
}
