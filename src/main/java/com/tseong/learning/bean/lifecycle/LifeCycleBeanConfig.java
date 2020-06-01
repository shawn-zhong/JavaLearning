package com.tseong.learning.bean.lifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Shawn ZHONG @date: 2020-05-29 17:34
 */

@Configuration
public class LifeCycleBeanConfig {

    @Bean(initMethod = "init", destroyMethod = "destroy", name = "shawnscyclebean")
    public LifeCycleBean getBean() {
        return new LifeCycleBean();
    }
}
