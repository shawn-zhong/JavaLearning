package com.tseong.learning.opensources.zookeeper.configManager;

import javax.mail.search.IntegerComparisonTerm;

public class ConfigTester {

    public static void main(String[] args) {
        ConfigManager manager = new ConfigManager();
        Configuration config = manager.dowloadConfigFromDB();
        System.out.println("downloaded config from DB : " + config.toString() );

        manager.syncConfigToZookeeper();
        System.out.println("synched the config to zookpper ");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        manager.uploadConfigToDB("newUser", "newPwd");
        System.out.println("uploaded new config to DB : " + config.toString());

        manager.syncConfigToZookeeper();
        System.out.println("synched new config to zookeeper.");
    }
}
