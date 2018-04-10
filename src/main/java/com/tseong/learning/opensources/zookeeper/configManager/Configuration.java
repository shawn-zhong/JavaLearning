package com.tseong.learning.opensources.zookeeper.configManager;

// https://blog.csdn.net/qi49125/article/details/60779877

import java.io.Serializable;

public class Configuration implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userNanme;
    private String userPassword;

    public Configuration(String userName, String userPwd) {
        this.userNanme = userName;
        this.userPassword = userPwd;
    }

    public String getUserNanme() {
        return userNanme;
    }

    public void setUserNanme(String userNanme) {
        this.userNanme = userNanme;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return String.format("Configuration {userName:%s, userPassword:%s}", userNanme, userPassword);
    }
}
