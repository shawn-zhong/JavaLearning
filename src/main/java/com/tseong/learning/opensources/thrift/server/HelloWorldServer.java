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


    /*
    
    thrift包含三个主要组件:

    - Protocal : TBinaryProtocal, TCompactProtocal, TJSONProtocal, TDebugProtocal
    - transport
    - Server :
        1) TSimpleServer（测试用） : 吱哟一个线程阻塞式接受和处理请求，所以只能服务于一个客户端，其它客户端在被连接之前只能等待
        2) TNonblokingServer : 使用了NIO Selector技术，但只有一个线程接受请求和处理读写，其它连接还是要等待
        3) THSHaServer : 类似上面但使用一个独立的worker线程池来处理消息，使用一个线程来处理网络I/O和数据读取
        4）TThreadPoolServer : 数据读取和业务处理都会交给线程池处理；主线程or单线程只负责监听新连接
        5) TThreadedSelectorServer : 使用了多线程来处理I/O. 维护了两个线程池 (优势在于网络I/O是瓶颈时)

     */
