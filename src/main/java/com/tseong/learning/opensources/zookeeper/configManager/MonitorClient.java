package com.tseong.learning.opensources.zookeeper.configManager;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class MonitorClient {

    private Configuration config;

    public Configuration getConfig() {
        ZkClient zkClient = new ZkClient("localhost:2181");
        config = (Configuration)zkClient.readData("/zkConfig");
        System.out.println("Read the configuration from zookeeper: " + config.toString());

        // listening the changes
        zkClient.subscribeDataChanges("/zkConfig", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                config = (Configuration) o;
                System.out.println("detected the config changes from zookeeper: " + config.toString());
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                config = null;
                System.out.println("detected the config deletion from zookeeper: " + config.toString());
            }
        });

        return config;
    }

    public static void main(String[] args) {
        MonitorClient client = new MonitorClient();
        client.getConfig();

        for (int i=0; i<10; i++) {
            System.out.println(client.config.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }
}
