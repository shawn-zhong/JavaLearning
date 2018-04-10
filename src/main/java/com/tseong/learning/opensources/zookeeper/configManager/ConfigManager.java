package com.tseong.learning.opensources.zookeeper.configManager;

import org.I0Itec.zkclient.ZkClient;

public class ConfigManager {

    private Configuration config;

    public Configuration dowloadConfigFromDB() {
        config = new Configuration("shawn", "a123");
        return config;
    }

    public void uploadConfigToDB(String name, String password) {
        if (config == null)
            config = new Configuration("def", "def");

        config.setUserNanme(name);
        config.setUserPassword(password);
    }

    public void syncConfigToZookeeper() {
        ZkClient zkClient = new ZkClient("localhost:2181");
        if (!zkClient.exists("/zkConfig")) {
            zkClient.createPersistent("/zkConfig", true);
        }

        zkClient.writeData("/zkConfig", config);
        zkClient.close();
    }
}
