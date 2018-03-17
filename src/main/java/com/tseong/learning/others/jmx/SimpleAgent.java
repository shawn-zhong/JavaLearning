package com.tseong.learning.others.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class SimpleAgent {

    private MBeanServer mbs = null;

    public SimpleAgent() {
        mbs = ManagementFactory.getPlatformMBeanServer();
        Hello helloBean = new Hello();
        ObjectName helloName = null;

        try {
            helloName = new ObjectName("FOO:name=HelloBean");
            mbs.registerMBean(helloBean, helloName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void waitForEnterPressed() {
        try {
            System.out.println("Press to continue ...");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SimpleAgent agent = new SimpleAgent();
        System.out.println("SimpleAgent is running ..");
        SimpleAgent.waitForEnterPressed();
    }
}


// refer to http://alvinalexander.com/blog/post/java/source-code-java-jmx-hello-world-application

/*

java -Dcom.sun.management.jmxremote \
     -Dcom.sun.management.jmxremote.port=1617 \
     -Dcom.sun.management.jmxremote.authenticate=false \
     -Dcom.sun.management.jmxremote.ssl=false \
     SimpleAgent

 */

// java -jar -Dcom.sun.management.jmxremote.port=1617 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false JMXDemo.jar
// use jconsole to view the MBean