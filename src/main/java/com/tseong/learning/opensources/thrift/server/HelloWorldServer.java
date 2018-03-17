package com.tseong.learning.opensources.thrift.server;

import com.tseong.learning.opensources.thrift.HelloWorldService;
import com.tseong.learning.opensources.thrift.client.HelloWorldImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

public class HelloWorldServer {
    public static final int SERVER_PORT = 8099;

    public static void main(String[] args) {
        HelloWorldServer server = new HelloWorldServer();
        server.startServer();
    }

    public void startServer() {
        try {
            System.out.println("starting server ..");
            TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());

            // 简单的单线程服务模型，一般用于测试
            TServerSocket serverTransport = new TServerSocket(SERVER_PORT);

            /*
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());   // TCompactProtocal TJSONProtocol

            TServer server = new TSimpleServer(tArgs);
            */


            // 线程池服务模型，使用标准的阻塞式IO, 预先创建一组线程处理请求

            TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(serverTransport);
            ttpsArgs.processor(tprocessor);
            ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TThreadPoolServer(ttpsArgs);
            server.serve();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
