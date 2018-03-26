package com.tseong.learning.opensources.nettyDiscard;

public class BootStrap {

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8383;
        }

        new DiscardServer(port).run();
        System.out.print("server is now running");
    }
}


/*

可以直接使用telnet来测试：

telnet 127.0.0.1 8383

 */